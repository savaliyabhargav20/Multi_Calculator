package com.example.multicalculator.domain.calculator

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalculationHistory(
    private val maxItems: Int = 100
) {
    private val _items = MutableStateFlow<List<CalculationHistoryItem>>(emptyList())
    val items: StateFlow<List<CalculationHistoryItem>> = _items.asStateFlow()

    fun addHistory(expression: String, result: String) {
        if (expression.isBlank() || result.isBlank() || result == "Error") return
        val newItem = CalculationHistoryItem(
            expression = expression.trim(),
            result = result.trim()
        )
        val current = _items.value.toMutableList()
        current.add(0, newItem) // Add to top
        if (current.size > maxItems) {
            current.removeAt(current.lastIndex)
        }
        _items.value = current
    }

    fun clearHistory() {
        _items.value = emptyList()
    }
}
