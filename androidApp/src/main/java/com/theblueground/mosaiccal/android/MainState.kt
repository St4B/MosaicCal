package com.theblueground.mosaiccal.android

import com.theblueground.mosaiccal.core.MonthGrid
import com.theblueground.mosaiccal.ui.MosaicCalDayItem
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

data class MainState(
    val isInRangeMode: Boolean,
    val daysOfWeek: List<DayOfWeek>,
    val months: List<MonthGrid<MosaicCalDayItem>>,
    val selectedDates: Set<LocalDate>,
    val selectedDays: Set<DayOfWeek>,
)