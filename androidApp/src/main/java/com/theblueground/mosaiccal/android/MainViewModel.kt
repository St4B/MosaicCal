package com.theblueground.mosaiccal.android

import androidx.lifecycle.ViewModel
import com.theblueground.mosaiccal.core.MosaicDataProvider
import com.theblueground.mosaiccal.ui.mapperAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class MainViewModel : ViewModel() {

    private val from = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    private val to = from.plus(3,  DateTimeUnit.YEAR)

    private val mosaicDataProvider = MosaicDataProvider()

    private val _state = MutableStateFlow(
        MainState(
            isInRangeMode = false,
            daysOfWeek = emptyList(),
            months = emptyList(),
            selectedDates = emptySet(),
            selectedDays = emptySet(),
        )
    )
    val state = _state.asStateFlow()

    fun processIntent(intent: MainIntent): Unit = when (intent) {
        is MainIntent.ToggleMode -> handleToggleMode()
        MainIntent.LoadData -> handleLoadData()
        is MainIntent.SelectDate -> handleSelectDate(date = intent.date)
    }

    private fun handleToggleMode() {
        val currentState = state.value
        val willBeInRangeMode = !currentState.isInRangeMode

        val minSelectedDate = currentState.selectedDates.minOrNull()
        val selectedDates = minSelectedDate?.let { setOf(it) } ?: emptySet()
        val selectedDays = minSelectedDate?.let { setOf(it.dayOfWeek) } ?: emptySet()
        val mapper = mapperAdapter(
            isInRangeMode = willBeInRangeMode,
            selectedDates = selectedDates,
            selectedDays = selectedDays,
        )

        _state.value = currentState.copy(
            isInRangeMode = willBeInRangeMode,
            months = mosaicDataProvider.provideMonths(
                from = from,
                to = to,
                mapper = mapper,
            ),
            selectedDates = selectedDates,
            selectedDays = selectedDays,
        )
    }

    private fun handleLoadData() {
        val currentState = state.value
        val mapper = mapperAdapter(
            isInRangeMode = currentState.isInRangeMode,
            selectedDates = currentState.selectedDates,
            selectedDays = currentState.selectedDays,
        )

        _state.value = currentState.copy(
            daysOfWeek = mosaicDataProvider.provideDaysOfWeek(),
            months = mosaicDataProvider.provideMonths(
                from = from,
                to = to,
                mapper = mapper,
            ),
        )
    }

    private fun handleSelectDate(date: LocalDate) {
        val currentState = state.value
        val hasOnlyOneDateSelected = currentState.selectedDates.size == 1
        val isInRangeMode = currentState.isInRangeMode

        val selectedDates = if (hasOnlyOneDateSelected && isInRangeMode) {
            currentState.selectedDates + date
        } else {
            setOf(date)
        }
        val selectedDays = selectedDates.map { it.dayOfWeek }.toSet()

        val mapper = mapperAdapter(
            isInRangeMode = isInRangeMode,
            selectedDates = selectedDates,
            selectedDays = selectedDays,
        )

        _state.value = currentState.copy(
            months = mosaicDataProvider.provideMonths(
                from = from,
                to = to,
                mapper = mapper,
            ),
            selectedDates = selectedDates,
            selectedDays = selectedDays,
        )
    }
}