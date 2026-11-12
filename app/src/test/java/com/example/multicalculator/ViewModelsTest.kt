package com.example.multicalculator

import com.example.multicalculator.domain.bmi.BmiCategory
import com.example.multicalculator.domain.bmi.BmiUnitSystem
import com.example.multicalculator.domain.calculator.AngleUnit
import com.example.multicalculator.domain.converter.ConverterCategory
import com.example.multicalculator.ui.bmi.BmiViewModel
import com.example.multicalculator.ui.calculator.CalculatorViewModel
import com.example.multicalculator.ui.converter.ConverterViewModel
import com.example.multicalculator.ui.date.DateCalculatorMode
import com.example.multicalculator.ui.date.DateViewModel
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class ViewModelsTest {

    @Test
    fun testCalculatorViewModelState() {
        val viewModel = CalculatorViewModel()
        viewModel.onInput("5")
        viewModel.onInput("+")
        viewModel.onInput("5")
        assertEquals("5+5", viewModel.uiState.value.expression)

        viewModel.onEvaluate()
        assertEquals("10", viewModel.uiState.value.result)
        assertEquals(1, viewModel.uiState.value.history.size)

        viewModel.toggleAngleUnit()
        assertEquals(AngleUnit.RADIAN, viewModel.uiState.value.angleUnit)

        viewModel.onClear()
        assertEquals("", viewModel.uiState.value.expression)
        assertEquals("", viewModel.uiState.value.result)
    }

    @Test
    fun testConverterViewModelState() {
        val viewModel = ConverterViewModel()
        viewModel.onCategorySelected(ConverterCategory.LENGTH)
        assertEquals(ConverterCategory.LENGTH, viewModel.uiState.value.selectedCategory)

        viewModel.onInputValueChanged("10")
        assertEquals("10", viewModel.uiState.value.inputValue)
        assertNotNull(viewModel.uiState.value.outputValue)

        val oldFrom = viewModel.uiState.value.fromUnit
        val oldTo = viewModel.uiState.value.toUnit
        viewModel.onSwapUnits()
        assertEquals(oldTo, viewModel.uiState.value.fromUnit)
        assertEquals(oldFrom, viewModel.uiState.value.toUnit)
    }

    @Test
    fun testDateViewModelState() {
        val viewModel = DateViewModel()
        viewModel.setMode(DateCalculatorMode.DURATION_BETWEEN_DATES)

        val start = LocalDate.of(2024, 1, 1)
        val end = LocalDate.of(2025, 1, 1)
        viewModel.setDurationStartDate(start)
        viewModel.setDurationEndDate(end)

        assertNotNull(viewModel.uiState.value.durationResult)
        assertEquals(1, viewModel.uiState.value.durationResult?.years)
    }

    @Test
    fun testBmiViewModelState() {
        val viewModel = BmiViewModel()
        viewModel.setUnitSystem(BmiUnitSystem.METRIC)
        viewModel.updateHeightCm("180")
        viewModel.updateWeightKg("75")

        assertNotNull(viewModel.uiState.value.bmiResult)
        assertEquals(BmiCategory.NORMAL, viewModel.uiState.value.bmiResult?.category)
    }
}
