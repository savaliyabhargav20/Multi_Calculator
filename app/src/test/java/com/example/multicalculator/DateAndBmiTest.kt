package com.example.multicalculator

import com.example.multicalculator.domain.bmi.BmiCalculator
import com.example.multicalculator.domain.bmi.BmiCategory
import com.example.multicalculator.domain.date.DateCalculator
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class DateAndBmiTest {

    @Test
    fun testDateDuration() {
        val start = LocalDate.of(2020, 1, 1)
        val end = LocalDate.of(2023, 6, 15)

        val result = DateCalculator.calculateDuration(start, end)
        assertEquals(3, result.years)
        assertEquals(5, result.months)
        assertEquals(14, result.days)
    }

    @Test
    fun testDateOffsetAddition() {
        val start = LocalDate.of(2025, 1, 1)
        val result = DateCalculator.calculateOffset(
            startDate = start,
            years = 1,
            months = 2,
            days = 10,
            isAddition = true
        )
        assertEquals(LocalDate.of(2026, 3, 11), result.targetDate)
    }

    @Test
    fun testDateOffsetSubtraction() {
        val start = LocalDate.of(2025, 5, 20)
        val result = DateCalculator.calculateOffset(
            startDate = start,
            years = 0,
            months = 1,
            days = 5,
            isAddition = false
        )
        assertEquals(LocalDate.of(2025, 4, 15), result.targetDate)
    }

    @Test
    fun testBmiCalculatorMetric() {
        // Height 170cm, Weight 65kg -> BMI ~ 22.49 (Normal weight)
        val result = BmiCalculator.calculateMetric(170.0, 65.0)
        assertNotNull(result)
        assertEquals(22.5, result!!.bmiValue, 0.1)
        assertEquals(BmiCategory.NORMAL, result.category)
    }

    @Test
    fun testBmiCalculatorImperial() {
        // Height 5ft 7in (67 inches), Weight 143 lbs -> BMI ~ 22.39 (Normal weight)
        val result = BmiCalculator.calculateImperial(5.0, 7.0, 143.0)
        assertNotNull(result)
        assertEquals(22.4, result!!.bmiValue, 0.1)
        assertEquals(BmiCategory.NORMAL, result.category)
    }

    @Test
    fun testBmiCategories() {
        assertEquals(BmiCategory.UNDERWEIGHT, BmiCategory.fromBmi(17.0))
        assertEquals(BmiCategory.NORMAL, BmiCategory.fromBmi(22.0))
        assertEquals(BmiCategory.OVERWEIGHT, BmiCategory.fromBmi(27.0))
        assertEquals(BmiCategory.OBESE, BmiCategory.fromBmi(33.0))
    }
}
