package com.theblueground.mosaiccal.ui

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.window.ComposeUIViewController
import com.theblueground.mosaiccal.core.MonthGrid
import com.theblueground.mosaiccal.core.MosaicDataProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import platform.UIKit.UIViewController

//https://proandroiddev.com/compose-multiplatform-managing-ui-state-on-ios-45d37effeda9
object MosaicCalController {

    private val daysOfWeek = MutableStateFlow<List<DayOfWeek>>(emptyList())

    private val months = MutableStateFlow<List<MonthGrid<MosaicCalDayItem>>>(emptyList())

    fun makeUIViewController(
        onSelectDate: (LocalDate) -> Unit = {},
    ): UIViewController = ComposeUIViewController {
            val daysOfWeekState = daysOfWeek.collectAsState()
            val monthsState = months.collectAsState()

            MosaicCal(
                daysOfWeek = daysOfWeekState.value,
                months = monthsState.value,
                onSelectDate = onSelectDate,
            )
        }

    fun updateDaysOfWeek(daysOfWeek: List<DayOfWeek>) {
        this.daysOfWeek.value = daysOfWeek
    }

    fun updateMonths(months: List<MonthGrid<MosaicCalDayItem>>) {
        this.months.value = months
    }
}
