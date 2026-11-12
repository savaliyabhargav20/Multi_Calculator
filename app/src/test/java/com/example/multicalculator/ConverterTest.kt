package com.example.multicalculator

import com.example.multicalculator.domain.converter.ConverterCategory
import com.example.multicalculator.domain.converter.CurrencyConverter
import com.example.multicalculator.domain.converter.UnitConverter
import org.junit.Assert.assertEquals
import org.junit.Test

class ConverterTest {

    @Test
    fun testLengthConversion() {
        val kmToM = UnitConverter.convert(ConverterCategory.LENGTH, "km", "m", 1.5)
        assertEquals(1500.0, kmToM, 0.0001)

        val ftToIn = UnitConverter.convert(ConverterCategory.LENGTH, "ft", "in", 6.0)
        assertEquals(72.0, ftToIn, 0.0001)
    }

    @Test
    fun testWeightConversion() {
        val kgToG = UnitConverter.convert(ConverterCategory.WEIGHT, "kg", "g", 2.0)
        assertEquals(2000.0, kgToG, 0.0001)

        val lbToOz = UnitConverter.convert(ConverterCategory.WEIGHT, "lb", "oz", 1.0)
        assertEquals(16.0, lbToOz, 0.01)
    }

    @Test
    fun testTemperatureConversion() {
        val cToF = UnitConverter.convert(ConverterCategory.TEMPERATURE, "c", "f", 100.0)
        assertEquals(212.0, cToF, 0.0001)

        val cToK = UnitConverter.convert(ConverterCategory.TEMPERATURE, "c", "k", 0.0)
        assertEquals(273.15, cToK, 0.0001)

        val fToC = UnitConverter.convert(ConverterCategory.TEMPERATURE, "f", "c", 32.0)
        assertEquals(0.0, fToC, 0.0001)
    }

    @Test
    fun testSpeedConversion() {
        val kmhToMps = UnitConverter.convert(ConverterCategory.SPEED, "kmh", "mps", 36.0)
        assertEquals(10.0, kmhToMps, 0.0001)
    }

    @Test
    fun testCurrencyConversionAndFallback() {
        // Convert USD to EUR (offline default: 1 USD = 0.92 EUR)
        val usdToEur = CurrencyConverter.convertCurrency("USD", "EUR", 100.0)
        assertEquals(92.0, usdToEur, 0.0001)

        // Fallback for unknown currency code should return input amount safely
        val unknownConv = CurrencyConverter.convertCurrency("XYZ", "USD", 50.0)
        assertEquals(50.0, unknownConv, 0.0001)
    }
}
