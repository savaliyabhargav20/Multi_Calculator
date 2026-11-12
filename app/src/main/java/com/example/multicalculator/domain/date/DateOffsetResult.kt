package com.example.multicalculator.domain.date

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

data class DateOffsetResult(
    val startDate: LocalDate,
    val offsetYears: Int,
    val offsetMonths: Int,
    val offsetDays: Int,
    val isAddition: Boolean,
    val targetDate: LocalDate
) {
    val dayOfWeek: String
        get() = targetDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

    val formattedTargetDate: String
        get() = targetDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"))
}
