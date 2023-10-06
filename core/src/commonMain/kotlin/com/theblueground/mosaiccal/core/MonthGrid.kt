package com.theblueground.mosaiccal.core

import kotlinx.datetime.Month

data class MonthGrid<T : BaseDayItem>(
    val year: Int,
    val month: Month,
    val daysRows: List<DaysRow<T>>
)