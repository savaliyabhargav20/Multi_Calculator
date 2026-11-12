package com.example.multicalculator.ui.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.multicalculator.domain.calculator.AngleUnit
import com.example.multicalculator.domain.calculator.CalculationHistory
import com.example.multicalculator.domain.calculator.CalculationHistoryItem
import com.example.multicalculator.domain.calculator.MathEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class CalculatorViewModel(
    private val calculationHistory: CalculationHistory = CalculationHistory()
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    init {
        calculationHistory.items
            .onEach { historyList ->
                _uiState.update { it.copy(history = historyList) }
            }
            .launchIn(viewModelScope)
    }

    fun onInput(input: String) {
        _uiState.update { currentState ->
            val newExpr = if (currentState.result.isNotBlank() && isOperator(input)) {
                // Continue calculation with previous result if an operator is pressed
                currentState.result + input
            } else {
                currentState.expression + input
            }
            currentState.copy(
                expression = newExpr,
                errorMessage = null
            )
        }
        evaluateLive()
    }

    fun onClear() {
        _uiState.update {
            it.copy(
                expression = "",
                result = "",
                errorMessage = null
            )
        }
    }

    fun onDelete() {
        _uiState.update { currentState ->
            val expr = currentState.expression
            if (expr.isNotEmpty()) {
                val updatedExpr = expr.dropLast(1)
                currentState.copy(
                    expression = updatedExpr,
                    errorMessage = null
                )
            } else {
                currentState
            }
        }
        evaluateLive()
    }

    fun onEvaluate() {
        val currentState = _uiState.value
        val expr = currentState.expression.trim()
        if (expr.isBlank()) return

        when (val evalResult = MathEngine.evaluate(expr, currentState.angleUnit)) {
            is MathEngine.EvaluationResult.Success -> {
                calculationHistory.addHistory(expr, evalResult.formattedResult)
                _uiState.update {
                    it.copy(
                        result = evalResult.formattedResult,
                        history = calculationHistory.items.value,
                        errorMessage = null
                    )
                }
            }
            is MathEngine.EvaluationResult.Error -> {
                _uiState.update {
                    it.copy(
                        errorMessage = evalResult.message
                    )
                }
            }
        }
    }

    fun toggleAngleUnit() {
        _uiState.update { currentState ->
            val newUnit = if (currentState.angleUnit == AngleUnit.DEGREE) {
                AngleUnit.RADIAN
            } else {
                AngleUnit.DEGREE
            }
            currentState.copy(angleUnit = newUnit)
        }
        evaluateLive()
    }

    fun selectHistoryItem(item: CalculationHistoryItem) {
        _uiState.update {
            it.copy(
                expression = item.expression,
                result = item.result,
                errorMessage = null
            )
        }
    }

    fun clearHistory() {
        calculationHistory.clearHistory()
        _uiState.update { it.copy(history = emptyList()) }
    }

    fun toggleScientificExpanded() {
        _uiState.update {
            it.copy(isScientificExpanded = !it.isScientificExpanded)
        }
    }

    private fun evaluateLive() {
        val expr = _uiState.value.expression.trim()
        if (expr.isBlank()) {
            _uiState.update { it.copy(result = "") }
            return
        }

        when (val result = MathEngine.evaluate(expr, _uiState.value.angleUnit)) {
            is MathEngine.EvaluationResult.Success -> {
                _uiState.update { it.copy(result = result.formattedResult, errorMessage = null) }
            }
            is MathEngine.EvaluationResult.Error -> {
                // For live typing preview, don't show error message until evaluate button is pressed
                _uiState.update { it.copy(result = "") }
            }
        }
    }

    private fun isOperator(input: String): Boolean {
        return input in listOf("+", "-", "*", "/", "^", "%", "×", "÷", "−")
    }
}
