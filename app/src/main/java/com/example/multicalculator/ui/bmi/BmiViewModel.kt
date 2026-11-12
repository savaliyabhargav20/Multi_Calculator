package com.example.multicalculator.ui.bmi

import androidx.lifecycle.ViewModel
import com.example.multicalculator.domain.bmi.BmiCalculator
import com.example.multicalculator.domain.bmi.BmiUnitSystem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BmiViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BmiUiState())
    val uiState: StateFlow<BmiUiState> = _uiState.asStateFlow()

    init {
        recalculate()
    }

    fun setUnitSystem(system: BmiUnitSystem) {
        _uiState.update { it.copy(unitSystem = system, errorMessage = null) }
        recalculate()
    }

    fun updateHeightCm(cm: String) {
        val sanitized = sanitizeDecimal(cm)
        _uiState.update { it.copy(heightCm = sanitized, errorMessage = null) }
        recalculate()
    }

    fun updateWeightKg(kg: String) {
        val sanitized = sanitizeDecimal(kg)
        _uiState.update { it.copy(weightKg = sanitized, errorMessage = null) }
        recalculate()
    }

    fun updateHeightFeet(feet: String) {
        val sanitized = sanitizeDecimal(feet)
        _uiState.update { it.copy(heightFeet = sanitized, errorMessage = null) }
        recalculate()
    }

    fun updateHeightInches(inches: String) {
        val sanitized = sanitizeDecimal(inches)
        _uiState.update { it.copy(heightInches = sanitized, errorMessage = null) }
        recalculate()
    }

    fun updateWeightLbs(lbs: String) {
        val sanitized = sanitizeDecimal(lbs)
        _uiState.update { it.copy(weightLbs = sanitized, errorMessage = null) }
        recalculate()
    }

    private fun recalculate() {
        val state = _uiState.value
        if (state.unitSystem == BmiUnitSystem.METRIC) {
            val cm = state.heightCm.toDoubleOrNull()
            val kg = state.weightKg.toDoubleOrNull()
            if (cm == null || cm <= 0 || kg == null || kg <= 0) {
                _uiState.update { it.copy(bmiResult = null, errorMessage = "Please enter valid height and weight") }
                return
            }
            val result = BmiCalculator.calculateMetric(cm, kg)
            _uiState.update { it.copy(bmiResult = result, errorMessage = null) }
        } else {
            val feet = state.heightFeet.toDoubleOrNull() ?: 0.0
            val inches = state.heightInches.toDoubleOrNull() ?: 0.0
            val lbs = state.weightLbs.toDoubleOrNull()
            if ((feet <= 0 && inches <= 0) || lbs == null || lbs <= 0) {
                _uiState.update { it.copy(bmiResult = null, errorMessage = "Please enter valid height and weight") }
                return
            }
            val result = BmiCalculator.calculateImperial(feet, inches, lbs)
            _uiState.update { it.copy(bmiResult = result, errorMessage = null) }
        }
    }

    private fun sanitizeDecimal(value: String): String {
        val sanitized = value.filter { it.isDigit() || it == '.' }
        if (sanitized.count { it == '.' } > 1) {
            return value.dropLast(1)
        }
        return sanitized
    }
}
