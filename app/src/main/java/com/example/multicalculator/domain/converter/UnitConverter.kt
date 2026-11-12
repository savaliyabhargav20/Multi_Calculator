package com.example.multicalculator.domain.converter

import kotlin.math.abs
import kotlin.math.round

object UnitConverter {

    val lengthUnits = listOf(
        UnitItem("m", "Meter", "m", 1.0),
        UnitItem("km", "Kilometer", "km", 1000.0),
        UnitItem("cm", "Centimeter", "cm", 0.01),
        UnitItem("mm", "Millimeter", "mm", 0.001),
        UnitItem("um", "Micrometer", "μm", 1e-6),
        UnitItem("nm", "Nanometer", "nm", 1e-9),
        UnitItem("mi", "Mile", "mi", 1609.344),
        UnitItem("yd", "Yard", "yd", 0.9144),
        UnitItem("ft", "Foot", "ft", 0.3048),
        UnitItem("in", "Inch", "in", 0.0254),
        UnitItem("nmi", "Nautical Mile", "nmi", 1852.0)
    )

    val weightUnits = listOf(
        UnitItem("kg", "Kilogram", "kg", 1.0),
        UnitItem("g", "Gram", "g", 0.001),
        UnitItem("mg", "Milligram", "mg", 1e-6),
        UnitItem("t", "Metric Ton", "t", 1000.0),
        UnitItem("lb", "Pound", "lb", 0.45359237),
        UnitItem("oz", "Ounce", "oz", 0.028349523125)
    )

    val temperatureUnits = listOf(
        UnitItem("c", "Celsius", "°C", 1.0),
        UnitItem("f", "Fahrenheit", "°F", 1.0),
        UnitItem("k", "Kelvin", "K", 1.0)
    )

    val areaUnits = listOf(
        UnitItem("sq_m", "Square Meter", "m²", 1.0),
        UnitItem("sq_km", "Square Kilometer", "km²", 1_000_000.0),
        UnitItem("ha", "Hectare", "ha", 10_000.0),
        UnitItem("ac", "Acre", "ac", 4046.8564224),
        UnitItem("sq_ft", "Square Foot", "sq ft", 0.09290304),
        UnitItem("sq_in", "Square Inch", "sq in", 0.00064516),
        UnitItem("sq_mi", "Square Mile", "sq mi", 2_589_988.110336)
    )

    val volumeUnits = listOf(
        UnitItem("l", "Liter", "L", 1.0),
        UnitItem("ml", "Milliliter", "mL", 0.001),
        UnitItem("cu_m", "Cubic Meter", "m³", 1000.0),
        UnitItem("gal", "US Gallon", "gal", 3.785411784),
        UnitItem("qt", "US Quart", "qt", 0.946352946),
        UnitItem("pt", "US Pint", "pt", 0.473176473),
        UnitItem("cup", "US Cup", "cup", 0.24),
        UnitItem("fl_oz", "Fluid Ounce", "fl oz", 0.0295735295625)
    )

    val speedUnits = listOf(
        UnitItem("mps", "Meters per Second", "m/s", 1.0),
        UnitItem("kmh", "Kilometers per Hour", "km/h", 1.0 / 3.6),
        UnitItem("mph", "Miles per Hour", "mph", 0.44704),
        UnitItem("kt", "Knots", "kn", 0.5144444444444444),
        UnitItem("fps", "Feet per Second", "ft/s", 0.3048)
    )

    fun getUnitsForCategory(category: ConverterCategory): List<UnitItem> {
        return when (category) {
            ConverterCategory.LENGTH -> lengthUnits
            ConverterCategory.WEIGHT -> weightUnits
            ConverterCategory.TEMPERATURE -> temperatureUnits
            ConverterCategory.AREA -> areaUnits
            ConverterCategory.VOLUME -> volumeUnits
            ConverterCategory.SPEED -> speedUnits
            ConverterCategory.CURRENCY -> CurrencyConverter.availableCurrencies
        }
    }

    fun convert(
        category: ConverterCategory,
        fromUnitId: String,
        toUnitId: String,
        value: Double
    ): Double {
        if (value.isNaN() || value.isInfinite()) return 0.0
        if (fromUnitId == toUnitId) return value

        if (category == ConverterCategory.TEMPERATURE) {
            return convertTemperature(fromUnitId, toUnitId, value)
        }

        if (category == ConverterCategory.CURRENCY) {
            return CurrencyConverter.convertCurrency(fromUnitId, toUnitId, value)
        }

        val units = getUnitsForCategory(category)
        val fromUnit = units.find { it.id == fromUnitId } ?: return value
        val toUnit = units.find { it.id == toUnitId } ?: return value

        if (fromUnit.factorToBase <= 0 || toUnit.factorToBase <= 0) return value

        val valueInBase = value * fromUnit.factorToBase
        return valueInBase / toUnit.factorToBase
    }

    private fun convertTemperature(fromId: String, toId: String, value: Double): Double {
        // Convert to Celsius first
        val celsius = when (fromId) {
            "c" -> value
            "f" -> (value - 32.0) * (5.0 / 9.0)
            "k" -> value - 273.15
            else -> value
        }

        // Convert Celsius to target
        return when (toId) {
            "c" -> celsius
            "f" -> celsius * (9.0 / 5.0) + 32.0
            "k" -> celsius + 273.15
            else -> celsius
        }
    }

    fun formatConvertedValue(value: Double): String {
        if (value.isNaN()) return "0"
        if (value.isInfinite()) return "Infinity"

        val rounded = round(value)
        if (abs(value - rounded) < 1e-9) {
            return rounded.toLong().toString()
        }

        val absVal = abs(value)
        if (absVal >= 1e9 || (absVal > 0 && absVal < 1e-4)) {
            return String.format("%.4e", value).replace("e+", "e")
        }

        return String.format("%.6f", value).trimEnd('0').trimEnd('.')
    }
}
