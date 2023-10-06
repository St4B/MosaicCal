package com.theblueground.mosaiccal.ui

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month
import java.time.format.TextStyle
import java.util.Locale

actual object LocalizedNameProvider {

    actual fun getDisplayName(dayOfWeek: DayOfWeek, style: DisplayStyle): String =
        dayOfWeek.getDisplayName(style.toTextStyle(), Locale.getDefault())

    actual fun getDisplayName(month: Month, style: DisplayStyle): String =
        month.getDisplayName(style.toTextStyle(), Locale.getDefault())

    private fun DisplayStyle.toTextStyle(): TextStyle {
        return when (this) {
            DisplayStyle.FULL -> TextStyle.FULL
            DisplayStyle.FULL_STANDALONE -> TextStyle.FULL_STANDALONE
            DisplayStyle.SHORT -> TextStyle.SHORT
            DisplayStyle.SHORT_STANDALONE -> TextStyle.SHORT_STANDALONE
            DisplayStyle.NARROW -> TextStyle.NARROW
            DisplayStyle.NARROW_STANDALONE -> TextStyle.NARROW_STANDALONE
        }
    }
}

