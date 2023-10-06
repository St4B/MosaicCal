package com.theblueground.mosaiccal.ui

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.onGloballyPositioned
import com.theblueground.mosaiccal.core.MonthGrid
import com.theblueground.mosaiccal.ui.theme.DarkMosaicCalThemeColorsColors
import com.theblueground.mosaiccal.ui.theme.LightMosaicCalThemeColorsColors
import com.theblueground.mosaiccal.ui.theme.MosaicCalDayCellStyle
import com.theblueground.mosaiccal.ui.theme.MosaicCalStyle
import com.theblueground.mosaiccal.ui.theme.MosaicCalTheme
import com.theblueground.mosaiccal.ui.theme.MosaicCalThemeColors
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

@Composable
fun MosaicCal(
    daysOfWeek: List<DayOfWeek>,
    months: List<MonthGrid<MosaicCalDayItem>>,
    onSelectDate: (LocalDate) -> Unit = {},
    dayTitle: @Composable RowScope.(DayOfWeek) -> Unit = { DayTitle(it) },
    monthHeader: @Composable ColumnScope.(Month) -> Unit = { MonthHeader(it) },
    day: @Composable RowScope.(MosaicCalDayItem) -> Unit = { DayCell(it, onSelectDate) },
    colors: MosaicCalThemeColors = if (isSystemInDarkTheme()) DarkMosaicCalThemeColorsColors else LightMosaicCalThemeColorsColors,
    style: MosaicCalStyle = MosaicCalStyle(),
) {
    MosaicCalTheme(colors = colors, style = style) {
        Column {
            Row { daysOfWeek.forEach { dayOfWeek -> dayTitle(dayOfWeek) } }
            LazyColumn {
                items(items = months) {
                    //
                    Column {
                        monthHeader(it.month)
                        it.daysRows.forEach {
                            Row { it.days.forEach { dayItem -> day(dayItem) } }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun RowScope.DayTitle(dayOfWeek: DayOfWeek) {
    val style = MosaicCalTheme.dayTitleStyle
    Box(modifier = Modifier.weight(weight = 1f)) {
        Text(
            modifier = Modifier.align(alignment = style.dayTitleAlignment),
            text = LocalizedNameProvider.getDisplayName(
                dayOfWeek = dayOfWeek,
                style = style.dayTitleNameStyle
            )
        )
    }
}

@Composable
private fun ColumnScope.MonthHeader(month: Month) {
    val style = MosaicCalTheme.monthHeaderStyle
    Text(
        modifier = Modifier.padding(
            start = style.monthHeaderStartPadding,
            top = style.monthHeaderTopPadding,
            end = style.monthHeaderEndPadding,
            bottom = style.monthHeaderBottomPadding
        ),
        text = LocalizedNameProvider.getDisplayName(
            month = month,
            style = style.monthHeaderNameStyle
        )
    )
}

@Composable
private fun RowScope.DayCell(
    dayItem: MosaicCalDayItem,
    onSelectDate: (LocalDate) -> Unit,
) {
    val width = remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .weight(weight = 1f)
            .onGloballyPositioned { coordinates -> width.value = coordinates.size.width.toFloat() },
    ) {

        DayCircle(
            dayItem = dayItem,
            cellWidth = width.value,
            onSelectDate = onSelectDate,
        )

        DayHighlight(dayItem = dayItem)
    }
}

@Composable
private fun BoxScope.DayCircle(
    dayItem: MosaicCalDayItem,
    cellWidth: Float,
    onSelectDate: (LocalDate) -> Unit,
) {
    val colors = MosaicCalTheme.colors
    val style = MosaicCalTheme.dayCellStyle

    if (!dayItem.isOutOfBounds) {
        val borderModifier = if (dayItem.isSelected && !dayItem.isInRange) {
            Modifier.border(
                width = style.dayCellBorder,
                color = colors.dayCellSelectedBorderColor,
                shape = style.dayCellShape
            )
        } else {
            Modifier
        }
        val backgroundModifier = if (dayItem.isFirstInRange || dayItem.isLastInRange) {
            Modifier.background(color = colors.rangeLimitColor, shape = style.dayCellShape)
        } else {
            Modifier
        }
        val enabledModifier = if (dayItem.isEnabled) {
            Modifier.clickable(onClick = { onSelectDate(dayItem.date) })
        } else {
            Modifier.alpha(alpha = style.dayCellDisabledAlpha)

        }

        val modifier = Modifier
            .align(Alignment.Center)
            .drawBehind {
                drawRangeBackground(
                    dayItem = dayItem,
                    cellWidth = cellWidth,
                    colors = colors,
                    style = style,
                )
            }
            .padding(all = style.dayCellPadding)
            .size(size = style.dayCellTextSize)
            .clip(shape = style.dayCellShape)
            .then(enabledModifier)
            .then(borderModifier)
            .then(backgroundModifier)

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier,
        ) {
            Text(
                text = dayItem.date.dayOfMonth.toString(),
            )
        }
    }

}

private fun DrawScope.drawRangeBackground(
    dayItem: MosaicCalDayItem,
    cellWidth: Float,
    colors: MosaicCalThemeColors,
    style: MosaicCalDayCellStyle,
) {

    val onAxisPadding = style.dayCellPadding * 2

    if (dayItem.isSelected && dayItem.isFirstInRange) {
        drawRect(
            color = colors.rangeBackgroundColor,
            topLeft = Offset(
                x = cellWidth / 2 - (cellWidth - size.width) / 2,
                y = style.dayCellPadding
                    .roundToPx()
                    .toFloat()
            ),
            size = Size(
                width = cellWidth / 2,
                height = size.height - onAxisPadding.roundToPx()
            )
        )
    } else if (dayItem.isSelected && dayItem.isLastInRange) {
        drawRect(
            color = colors.rangeBackgroundColor,
            topLeft = Offset(
                x = (size.width - cellWidth) / 2,
                y = style.dayCellPadding
                    .roundToPx()
                    .toFloat()
            ),
            size = Size(
                width = cellWidth / 2,
                height = size.height - onAxisPadding.roundToPx()
            )
        )
    } else if (dayItem.isInRange) {
        drawRect(
            color = colors.rangeBackgroundColor,
            topLeft = Offset(
                x = (size.width - cellWidth) / 2,
                y = style.dayCellPadding
                    .roundToPx()
                    .toFloat()
            ),
            size = Size(
                width = cellWidth,
                height = size.height - onAxisPadding.roundToPx()
            )
        )
    }
}

@Composable
private fun BoxScope.DayHighlight(dayItem: MosaicCalDayItem) {
    val colors = MosaicCalTheme.colors
    val style = MosaicCalTheme.dayHighlightStyle

    androidx.compose.animation.AnimatedVisibility(
        modifier = Modifier.align(alignment = style.dayHighlightAlignment),
        visible = dayItem.isHighlighted && !dayItem.isOutOfBounds,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .size(size = style.dayHighlightSize)
                .background(
                    color = colors.dayHighlightColor,
                    shape = style.dayHighlightShape
                )
        )
    }
}

