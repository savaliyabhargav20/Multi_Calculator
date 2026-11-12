package com.example.multicalculator.domain.converter

data class UnitItem(
    val id: String,
    val name: String,
    val symbol: String,
    val factorToBase: Double = 1.0 // Relative to base unit of category
)
