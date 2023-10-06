package com.theblueground.mosaiccal.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.theblueground.mosaiccal.ui.DisplayStyle

internal val LocalMosaicCalStyle = staticCompositionLocalOf<MosaicCalStyle> {
    error("No MosaicCalStyle provided")
}

data class MosaicCalStyle(
    val dayTitleStyle: MosaicCalDayTitleStyle = MosaicCalDayTitleStyle(),
    val monthHeaderStyle: MosaicCalMonthHeaderStyle = MosaicCalMonthHeaderStyle(),
    val dayHighlightStyle: MosaicCalDayHighlightStyle = MosaicCalDayHighlightStyle(),
    val dayCellStyle: MosaicCalDayCellStyle = MosaicCalDayCellStyle()
)

data class MosaicCalDayTitleStyle(
    val dayTitleAlignment: Alignment = Alignment.Center,
    val dayTitleNameStyle: DisplayStyle = DisplayStyle.NARROW_STANDALONE,
)

data class MosaicCalMonthHeaderStyle(
    val monthHeaderTopPadding: Dp = 24.dp,
    val monthHeaderStartPadding: Dp = 16.dp,
    val monthHeaderBottomPadding: Dp = 24.dp,
    val monthHeaderEndPadding: Dp = 16.dp,
    val monthHeaderNameStyle: DisplayStyle = DisplayStyle.FULL_STANDALONE,
)

data class MosaicCalDayHighlightStyle(
    val dayHighlightAlignment: Alignment = Alignment.BottomCenter,
    val dayHighlightSize: Dp = 8.dp,
    val dayHighlightShape: Shape = CircleShape,
)

data class MosaicCalDayCellStyle(
    val dayCellPadding: Dp = 7.dp,
    val dayCellBorder: Dp = 1.dp,
    val dayCellShape: Shape = CircleShape,
    val dayCellTextSize: Dp = 32.dp,
    val dayCellDisabledAlpha: Float = 0.4f,
)
