package com.example.multicalculator.domain.bmi

enum class BmiCategory(
    val label: String,
    val minBmi: Double,
    val maxBmi: Double,
    val rangeText: String
) {
    UNDERWEIGHT("Underweight", 0.0, 18.49, "< 18.5"),
    NORMAL("Normal weight", 18.5, 24.99, "18.5 – 24.9"),
    OVERWEIGHT("Overweight", 25.0, 29.99, "25.0 – 29.9"),
    OBESE("Obese", 30.0, Double.POSITIVE_INFINITY, "≥ 30.0");

    companion object {
        fun fromBmi(bmi: Double): BmiCategory {
            return when {
                bmi < 18.5 -> UNDERWEIGHT
                bmi < 25.0 -> NORMAL
                bmi < 30.0 -> OVERWEIGHT
                else -> OBESE
            }
        }
    }
}
