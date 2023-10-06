package com.theblueground.mosaiccal.ui

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month

expect object LocalizedNameProvider {

    fun getDisplayName(dayOfWeek: DayOfWeek, style: DisplayStyle): String

    fun getDisplayName(month: Month, style: DisplayStyle): String
}


enum class DisplayStyle {

    // ordered from large to small
    /**
     * Full text, typically the full description.
     * For example, day-of-week Monday might output "Monday".
     */
    FULL,

    /**
     * Full text for stand-alone use, typically the full description.
     * For example, day-of-week Monday might output "Monday".
     */
    FULL_STANDALONE,

    /**
     * Short text, typically an abbreviation.
     * For example, day-of-week Monday might output "Mon".
     */
    SHORT,

    /**
     * Short text for stand-alone use, typically an abbreviation.
     * For example, day-of-week Monday might output "Mon".
     */
    SHORT_STANDALONE,

    /**
     * Narrow text, typically a single letter.
     * For example, day-of-week Monday might output "M".
     */
    NARROW,

    /**
     * Narrow text for stand-alone use, typically a single letter.
     * For example, day-of-week Monday might output "M".
     */
    NARROW_STANDALONE;

}