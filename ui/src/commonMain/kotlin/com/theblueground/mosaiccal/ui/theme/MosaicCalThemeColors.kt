package com.theblueground.mosaiccal.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val LocalMosaicCalThemeColorsColors = staticCompositionLocalOf<MosaicCalThemeColors> {
    error("No LocalMosaicCalThemeColors provided")
}

internal val LightMosaicCalThemeColorsColors = MosaicCalThemeColors(
    dayHighlightColor = Color(0xFFD0BCFF),
    dayCellSelectedBorderColor = Color(0xFF6650a4),
    rangeLimitColor = Color(0xFF6650a4),
    rangeBackgroundColor = Color(0xFFD0BCFF),
)

internal val DarkMosaicCalThemeColorsColors = MosaicCalThemeColors(
    dayHighlightColor = Color(0xFFD0BCFF),
    dayCellSelectedBorderColor = Color(0xFF6650a4),
    rangeLimitColor = Color(0xFF6650a4),
    rangeBackgroundColor = Color(0xFFD0BCFF),
)

@Stable
class MosaicCalThemeColors(
    dayHighlightColor: Color,
    dayCellSelectedBorderColor: Color,
    rangeLimitColor: Color,
    rangeBackgroundColor: Color,
) {
    var dayHighlightColor by mutableStateOf(dayHighlightColor)
        private set

    var dayCellSelectedBorderColor by mutableStateOf(dayCellSelectedBorderColor)
        private set

    var rangeLimitColor by mutableStateOf(rangeLimitColor)
        private set

    var rangeBackgroundColor by mutableStateOf(rangeBackgroundColor)
        private set

    fun copy(
        dayHighlightColor: Color = this.dayHighlightColor,
        dayCellSelectedBorderColor: Color = this.dayCellSelectedBorderColor,
        rangeLimitColor: Color = this.rangeLimitColor,
        rangeBackgroundColor: Color = this.rangeBackgroundColor,
    ): MosaicCalThemeColors = MosaicCalThemeColors(
        dayHighlightColor = dayHighlightColor,
        dayCellSelectedBorderColor = dayCellSelectedBorderColor,
        rangeLimitColor = rangeLimitColor,
        rangeBackgroundColor = rangeBackgroundColor,
    )

    fun update(other: MosaicCalThemeColors) {
        dayHighlightColor = other.dayHighlightColor
        dayCellSelectedBorderColor = other.dayCellSelectedBorderColor
        rangeLimitColor = other.rangeLimitColor
        rangeBackgroundColor = other.rangeBackgroundColor
    }
}
