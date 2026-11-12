package com.example.multicalculator.domain.bmi

import kotlin.math.pow

object BmiCalculator {

    fun calculateMetric(heightCm: Double, weightKg: Double): BmiResult? {
        if (heightCm <= 0 || weightKg <= 0) return null

        val heightM = heightCm / 100.0
        val bmi = weightKg / heightM.pow(2)
        val category = BmiCategory.fromBmi(bmi)

        val minWeight = 18.5 * heightM.pow(2)
        val maxWeight = 24.9 * heightM.pow(2)

        return BmiResult(
            bmiValue = bmi,
            category = category,
            healthyWeightMin = minWeight,
            healthyWeightMax = maxWeight,
            unitSystem = BmiUnitSystem.METRIC
        )
    }

    fun calculateImperial(heightFeet: Double, heightInches: Double, weightLbs: Double): BmiResult? {
        val totalInches = heightFeet * 12.0 + heightInches
        if (totalInches <= 0 || weightLbs <= 0) return null

        val bmi = (703.0 * weightLbs) / totalInches.pow(2)
        val category = BmiCategory.fromBmi(bmi)

        val minWeight = (18.5 * totalInches.pow(2)) / 703.0
        val maxWeight = (24.9 * totalInches.pow(2)) / 703.0

        return BmiResult(
            bmiValue = bmi,
            category = category,
            healthyWeightMin = minWeight,
            healthyWeightMax = maxWeight,
            unitSystem = BmiUnitSystem.IMPERIAL
        )
    }
}
