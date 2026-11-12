package com.example.multicalculator.ui.date

import com.example.multicalculator.domain.date.DateDurationResult
import com.example.multicalculator.domain.date.DateOffsetResult
import java.time.LocalDate

data class DateUiState(
    val mode: DateCalculatorMode = DateCalculatorMode.DURATION_BETWEEN_DATES,
    val durationStartDate: LocalDate = LocalDate.now(),
    val durationEndDate: LocalDate = LocalDate.now().plusDays(1),
    val durationResult: DateDurationResult? = null,
    val offsetBaseDate: LocalDate = LocalDate.now(),
    val offsetYears: Int = 0,
    val offsetMonths: Int = 0,
    val offsetDays: Int = 0,
    val isAddition: Boolean = true,
    val offsetResult: DateOffsetResult? = null
)
