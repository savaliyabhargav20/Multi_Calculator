package com.example.multicalculator.ui.date

import androidx.lifecycle.ViewModel
import com.example.multicalculator.domain.date.DateCalculator
import java.time.LocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DateViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DateUiState())
    val uiState: StateFlow<DateUiState> = _uiState.asStateFlow()

    init {
        recalculate()
    }

    fun setMode(mode: DateCalculatorMode) {
        _uiState.update { it.copy(mode = mode) }
        recalculate()
    }

    fun setDurationStartDate(date: LocalDate) {
        _uiState.update { it.copy(durationStartDate = date) }
        recalculate()
    }

    fun setDurationEndDate(date: LocalDate) {
        _uiState.update { it.copy(durationEndDate = date) }
        recalculate()
    }

    fun setOffsetBaseDate(date: LocalDate) {
        _uiState.update { it.copy(offsetBaseDate = date) }
        recalculate()
    }

    fun setOffsetYears(years: Int) {
        _uiState.update { it.copy(offsetYears = years.coerceAtLeast(0)) }
        recalculate()
    }

    fun setOffsetMonths(months: Int) {
        _uiState.update { it.copy(offsetMonths = months.coerceAtLeast(0)) }
        recalculate()
    }

    fun setOffsetDays(days: Int) {
        _uiState.update { it.copy(offsetDays = days.coerceAtLeast(0)) }
        recalculate()
    }

    fun setIsAddition(isAddition: Boolean) {
        _uiState.update { it.copy(isAddition = isAddition) }
        recalculate()
    }

    fun swapDurationDates() {
        _uiState.update {
            it.copy(
                durationStartDate = it.durationEndDate,
                durationEndDate = it.durationStartDate
            )
        }
        recalculate()
    }

    private fun recalculate() {
        val state = _uiState.value
        when (state.mode) {
            DateCalculatorMode.DURATION_BETWEEN_DATES -> {
                val duration = DateCalculator.calculateDuration(
                    state.durationStartDate,
                    state.durationEndDate
                )
                _uiState.update { it.copy(durationResult = duration) }
            }
            DateCalculatorMode.TARGET_DATE_OFFSET -> {
                val offset = DateCalculator.calculateOffset(
                    startDate = state.offsetBaseDate,
                    years = state.offsetYears,
                    months = state.offsetMonths,
                    days = state.offsetDays,
                    isAddition = state.isAddition
                )
                _uiState.update { it.copy(offsetResult = offset) }
            }
        }
    }
}
