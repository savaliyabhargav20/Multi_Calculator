package com.example.multicalculator.ui.bmi

import com.example.multicalculator.domain.bmi.BmiResult
import com.example.multicalculator.domain.bmi.BmiUnitSystem

data class BmiUiState(
    val unitSystem: BmiUnitSystem = BmiUnitSystem.METRIC,
    val heightCm: String = "170",
    val weightKg: String = "65",
    val heightFeet: String = "5",
    val heightInches: String = "7",
    val weightLbs: String = "143",
    val bmiResult: BmiResult? = null,
    val errorMessage: String? = null
)
