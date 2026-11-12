package com.example.multicalculator.ui.calculator

import com.example.multicalculator.domain.calculator.AngleUnit
import com.example.multicalculator.domain.calculator.CalculationHistoryItem

data class CalculatorUiState(
    val expression: String = "",
    val result: String = "",
    val angleUnit: AngleUnit = AngleUnit.DEGREE,
    val history: List<CalculationHistoryItem> = emptyList(),
    val errorMessage: String? = null,
    val isScientificExpanded: Boolean = false
)
