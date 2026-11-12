package com.example.multicalculator.domain.date

import java.time.LocalDate

data class DateDurationResult(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val years: Int,
    val months: Int,
    val days: Int,
    val totalDays: Long,
    val totalWeeks: Long,
    val remainingDaysInWeeks: Long,
    val totalMonths: Long,
    val isReversed: Boolean = false
) {
    fun toFormattedString(): String {
        val parts = mutableListOf<String>()
        if (years > 0) parts.add("$years ${if (years == 1) "year" else "years"}")
        if (months > 0) parts.add("$months ${if (months == 1) "month" else "months"}")
        if (days > 0 || parts.isEmpty()) parts.add("$days ${if (days == 1) "day" else "days"}")
        return parts.joinToString(", ")
    }
}
