package com.theblueground.mosaiccal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember

object MosaicCalTheme {

    val colors: MosaicCalThemeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalMosaicCalThemeColorsColors.current

    val dayTitleStyle: MosaicCalDayTitleStyle
        @Composable
        @ReadOnlyComposable
        get() = LocalMosaicCalStyle.current.dayTitleStyle

    val monthHeaderStyle: MosaicCalMonthHeaderStyle
        @Composable
        @ReadOnlyComposable
        get() = LocalMosaicCalStyle.current.monthHeaderStyle

    val dayHighlightStyle: MosaicCalDayHighlightStyle
        @Composable
        @ReadOnlyComposable
        get() = LocalMosaicCalStyle.current.dayHighlightStyle

    val dayCellStyle: MosaicCalDayCellStyle
        @Composable
        @ReadOnlyComposable
        get() = LocalMosaicCalStyle.current.dayCellStyle
}

@Composable
fun MosaicCalTheme(
    colors: MosaicCalThemeColors,
    style: MosaicCalStyle = MosaicCalStyle(),
    content: @Composable () -> Unit,
) {
    val colorPalette = remember {
        // Explicitly creating a new object here so we don't mutate the initial [colors]
        // provided, and overwrite the values set in it.
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(
        LocalMosaicCalThemeColorsColors provides colorPalette,
        LocalMosaicCalStyle provides style,
        content = content,
    )
}