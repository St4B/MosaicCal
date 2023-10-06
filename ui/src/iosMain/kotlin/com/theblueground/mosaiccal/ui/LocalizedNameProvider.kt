package com.theblueground.mosaiccal.ui

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month
import kotlinx.datetime.isoDayNumber
import platform.Foundation.NSCalendar

actual object LocalizedNameProvider {

    actual fun getDisplayName(
        dayOfWeek: DayOfWeek,
        style: DisplayStyle
    ): String {
        val calendar = NSCalendar.currentCalendar

        val symbols = when (style) {
            DisplayStyle.FULL -> calendar.weekdaySymbols
            DisplayStyle.FULL_STANDALONE -> calendar.standaloneWeekdaySymbols
            DisplayStyle.SHORT -> calendar.shortWeekdaySymbols
            DisplayStyle.SHORT_STANDALONE -> calendar.shortStandaloneWeekdaySymbols
            DisplayStyle.NARROW -> calendar.veryShortWeekdaySymbols
            DisplayStyle.NARROW_STANDALONE -> calendar.veryShortStandaloneWeekdaySymbols
        }

        return symbols[(dayOfWeek.ordinal + 1) % 7].toString()
    }

    actual fun getDisplayName(
        month: Month,
        style: DisplayStyle
    ): String {
        val calendar = NSCalendar.currentCalendar

        val symbols = when (style) {
            DisplayStyle.FULL -> calendar.monthSymbols
            DisplayStyle.FULL_STANDALONE -> calendar.standaloneMonthSymbols
            DisplayStyle.SHORT -> calendar.shortMonthSymbols
            DisplayStyle.SHORT_STANDALONE -> calendar.shortStandaloneMonthSymbols
            DisplayStyle.NARROW -> calendar.veryShortMonthSymbols
            DisplayStyle.NARROW_STANDALONE -> calendar.veryShortStandaloneMonthSymbols
        }

        return symbols[month.ordinal].toString()
    }
}

