package com.example.multicalculator.domain.date

import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit
import kotlin.math.abs

object DateCalculator {

    fun calculateDuration(startDate: LocalDate, endDate: LocalDate): DateDurationResult {
        val isReversed = startDate.isAfter(endDate)
        val fromDate = if (isReversed) endDate else startDate
        val toDate = if (isReversed) startDate else endDate

        val period = Period.between(fromDate, toDate)
        val totalDays = ChronoUnit.DAYS.between(fromDate, toDate)
        val totalMonths = ChronoUnit.MONTHS.between(fromDate, toDate)

        val totalWeeks = totalDays / 7
        val remainingDays = totalDays % 7

        return DateDurationResult(
            startDate = startDate,
            endDate = endDate,
            years = period.years,
            months = period.months,
            days = period.days,
            totalDays = if (isReversed) -totalDays else totalDays,
            totalWeeks = totalWeeks,
            remainingDaysInWeeks = remainingDays,
            totalMonths = totalMonths,
            isReversed = isReversed
        )
    }

    fun calculateOffset(
        startDate: LocalDate,
        years: Int,
        months: Int,
        days: Int,
        isAddition: Boolean
    ): DateOffsetResult {
        val absYears = abs(years)
        val absMonths = abs(months)
        val absDays = abs(days)

        val targetDate = if (isAddition) {
            startDate
                .plusYears(absYears.toLong())
                .plusMonths(absMonths.toLong())
                .plusDays(absDays.toLong())
        } else {
            startDate
                .minusYears(absYears.toLong())
                .minusMonths(absMonths.toLong())
                .minusDays(absDays.toLong())
        }

        return DateOffsetResult(
            startDate = startDate,
            offsetYears = absYears,
            offsetMonths = absMonths,
            offsetDays = absDays,
            isAddition = isAddition,
            targetDate = targetDate
        )
    }
}
