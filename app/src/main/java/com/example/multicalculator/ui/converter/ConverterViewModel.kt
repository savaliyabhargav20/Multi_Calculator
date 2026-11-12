package com.example.multicalculator.ui.converter

import androidx.lifecycle.ViewModel
import com.example.multicalculator.domain.converter.ConverterCategory
import com.example.multicalculator.domain.converter.CurrencyConverter
import com.example.multicalculator.domain.converter.UnitConverter
import com.example.multicalculator.domain.converter.UnitItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ConverterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ConverterUiState())
    val uiState: StateFlow<ConverterUiState> = _uiState.asStateFlow()

    init {
        onCategorySelected(ConverterCategory.LENGTH)
    }

    fun onCategorySelected(category: ConverterCategory) {
        val units = UnitConverter.getUnitsForCategory(category)
        val defaultFrom = units.getOrNull(0)
        val defaultTo = units.getOrNull(1) ?: defaultFrom

        _uiState.update {
            it.copy(
                selectedCategory = category,
                availableUnits = units,
                fromUnit = defaultFrom,
                toUnit = defaultTo,
                isOfflineCurrency = category == ConverterCategory.CURRENCY,
                errorMessage = null
            )
        }
        recalculate()
    }

    fun onFromUnitSelected(unit: UnitItem) {
        _uiState.update { it.copy(fromUnit = unit) }
        recalculate()
    }

    fun onToUnitSelected(unit: UnitItem) {
        _uiState.update { it.copy(toUnit = unit) }
        recalculate()
    }

    fun onInputValueChanged(value: String) {
        // Sanitize input (allow single decimal point, numbers)
        val sanitized = value.filter { it.isDigit() || it == '.' }
        if (sanitized.count { it == '.' } > 1) return

        _uiState.update { it.copy(inputValue = sanitized, errorMessage = null) }
        recalculate()
    }

    fun onSwapUnits() {
        val currentFrom = _uiState.value.fromUnit
        val currentTo = _uiState.value.toUnit
        _uiState.update {
            it.copy(
                fromUnit = currentTo,
                toUnit = currentFrom
            )
        }
        recalculate()
    }

    fun refreshCurrencyRates() {
        // Safe fallback mechanism: resets or updates offline rates
        CurrencyConverter.resetToDefaultRates()
        recalculate()
    }

    private fun recalculate() {
        val state = _uiState.value
        val from = state.fromUnit
        val to = state.toUnit

        if (from == null || to == null || state.inputValue.isBlank()) {
            _uiState.update { it.copy(outputValue = "") }
            return
        }

        val inputDouble = state.inputValue.toDoubleOrNull()
        if (inputDouble == null) {
            _uiState.update { it.copy(outputValue = "", errorMessage = "Invalid input number") }
            return
        }

        val converted = UnitConverter.convert(
            category = state.selectedCategory,
            fromUnitId = from.id,
            toUnitId = to.id,
            value = inputDouble
        )

        val formatted = UnitConverter.formatConvertedValue(converted)
        _uiState.update {
            it.copy(
                outputValue = formatted,
                errorMessage = null
            )
        }
    }
}
