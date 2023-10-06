package com.theblueground.mosaiccal.core

import kotlinx.datetime.DayOfWeek
import java.time.temporal.WeekFields
import java.util.Locale

actual object LocaleBasedValues {

    actual val firstDayOfWeek: DayOfWeek
        get() = WeekFields.of(Locale.getDefault()).firstDayOfWeek
}