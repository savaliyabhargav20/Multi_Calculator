package com.example.multicalculator.domain.bmi

import kotlin.math.roundToInt

data class BmiResult(
    val bmiValue: Double,
    val category: BmiCategory,
    val healthyWeightMin: Double,
    val healthyWeightMax: Double,
    val unitSystem: BmiUnitSystem
) {
    val formattedBmi: String
        get() = String.format("%.1f", bmiValue)

    val formattedHealthyRange: String
        get() {
            val unitLabel = if (unitSystem == BmiUnitSystem.METRIC) "kg" else "lbs"
            val minStr = String.format("%.1f", healthyWeightMin)
            val maxStr = String.format("%.1f", healthyWeightMax)
            return "$minStr – $maxStr $unitLabel"
        }
}
