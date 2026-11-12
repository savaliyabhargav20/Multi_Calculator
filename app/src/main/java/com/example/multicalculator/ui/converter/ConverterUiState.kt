package com.example.multicalculator.ui.converter

import com.example.multicalculator.domain.converter.ConverterCategory
import com.example.multicalculator.domain.converter.UnitItem

data class ConverterUiState(
    val selectedCategory: ConverterCategory = ConverterCategory.LENGTH,
    val availableUnits: List<UnitItem> = emptyList(),
    val fromUnit: UnitItem? = null,
    val toUnit: UnitItem? = null,
    val inputValue: String = "1",
    val outputValue: String = "",
    val isOfflineCurrency: Boolean = true,
    val errorMessage: String? = null
)
