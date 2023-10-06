package com.theblueground.mosaiccal.core

import kotlinx.datetime.DayOfWeek
import platform.Foundation.NSCalendar

actual object LocaleBasedValues {

    actual val firstDayOfWeek: DayOfWeek
        get() {
            val calendar = NSCalendar.currentCalendar

            return when (val firstWeekDay = calendar.firstWeekday.toInt()) {
                1 -> DayOfWeek.SUNDAY
                2 -> DayOfWeek.MONDAY
                3 -> DayOfWeek.TUESDAY
                4 -> DayOfWeek.WEDNESDAY
                5 -> DayOfWeek.THURSDAY
                6 -> DayOfWeek.FRIDAY
                7 -> DayOfWeek.SATURDAY
                else -> throw IllegalStateException("Unexpected firstWeekDay: $firstWeekDay")
            }
        }
}