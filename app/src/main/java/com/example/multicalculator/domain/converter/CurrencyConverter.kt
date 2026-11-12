package com.example.multicalculator.domain.converter

object CurrencyConverter {

    val availableCurrencies = listOf(
        UnitItem("USD", "US Dollar", "$", 1.0),
        UnitItem("EUR", "Euro", "€", 0.92),
        UnitItem("GBP", "British Pound", "£", 0.79),
        UnitItem("JPY", "Japanese Yen", "¥", 155.0),
        UnitItem("CAD", "Canadian Dollar", "CA$", 1.37),
        UnitItem("AUD", "Australian Dollar", "A$", 1.52),
        UnitItem("CHF", "Swiss Franc", "CHF", 0.90),
        UnitItem("CNY", "Chinese Yuan", "¥", 7.23),
        UnitItem("INR", "Indian Rupee", "₹", 83.50),
        UnitItem("BRL", "Brazilian Real", "R$", 5.40),
        UnitItem("MXN", "Mexican Peso", "MX$", 18.20),
        UnitItem("KRW", "South Korean Won", "₩", 1380.0)
    )

    private val defaultRatesToUsdBase: Map<String, Double> = availableCurrencies.associate {
        it.id to it.factorToBase
    }

    private var currentRates: MutableMap<String, Double> = defaultRatesToUsdBase.toMutableMap()

    fun resetToDefaultRates() {
        currentRates = defaultRatesToUsdBase.toMutableMap()
    }

    fun updateRates(newRates: Map<String, Double>) {
        newRates.forEach { (code, rate) ->
            if (rate > 0) {
                currentRates[code] = rate
            }
        }
    }

    fun getRate(currencyCode: String): Double {
        return currentRates[currencyCode] ?: defaultRatesToUsdBase[currencyCode] ?: 1.0
    }

    fun convertCurrency(fromCode: String, toCode: String, amount: Double): Double {
        if (amount.isNaN() || amount.isInfinite()) return 0.0
        if (fromCode == toCode) return amount

        val fromRate = getRate(fromCode)
        val toRate = getRate(toCode)

        // Standard conversion relative to USD base
        // amount / fromRate -> amount in USD
        // (amount / fromRate) * toRate -> amount in target currency
        if (fromRate <= 0.0 || toRate <= 0.0) return amount
        return (amount / fromRate) * toRate
    }
}
