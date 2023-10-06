package com.theblueground.mosaiccal.ui

import com.theblueground.mosaiccal.core.BaseDayItem
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

data class MosaicCalDayItem(
    override val date: LocalDate,
    override val isEnabled: Boolean,
    override val isSelected: Boolean,
    val isHighlighted: Boolean,
    val isFirstInRange: Boolean,
    val isLastInRange: Boolean,
    val isInRange: Boolean,
    val isOutOfBounds: Boolean,
) : BaseDayItem

fun mapperAdapter(
    isInRangeMode: Boolean,
    selectedDates: Set<LocalDate>,
    selectedDays: Set<DayOfWeek>,
): (LocalDate, Month) -> MosaicCalDayItem {
    val minSelectedDate = selectedDates.minOrNull()
    val maxSelectedDate = selectedDates.maxOrNull()

    return { date, monthOfGrid ->
        val isEnabled = computeIsEnabled(date)

        MosaicCalDayItem(
            date = date,
            isEnabled = isEnabled,
            isSelected = computeIsSelected(date, selectedDates),
            isHighlighted = computeIsHighlighted(date, minSelectedDate, selectedDays, isInRangeMode),
            isFirstInRange = computeIsFirstInRange(date, minSelectedDate, maxSelectedDate, isInRangeMode),
            isLastInRange = computeIsLastInRange(date, minSelectedDate, maxSelectedDate,isInRangeMode),
            isInRange = computeIsInRange(date, minSelectedDate, maxSelectedDate,isInRangeMode, isEnabled),
            isOutOfBounds = computeIsOutOfBounds(date, monthOfGrid),
        )
    }
}

private fun computeIsEnabled(
    date: LocalDate,
): Boolean {
    // Fixme: This wont work for all locales
    return date.dayOfWeek != DayOfWeek.SATURDAY && date.dayOfWeek != DayOfWeek.SUNDAY
}

private fun computeIsSelected(
    date: LocalDate,
    selectedDates: Set<LocalDate>,
): Boolean = date in selectedDates

private fun computeIsHighlighted(
    date: LocalDate,
    minSelectedDate: LocalDate?,
    selectedDays: Set<DayOfWeek>,
    isInRangeMode: Boolean,
): Boolean {
    minSelectedDate ?: return false
    return date > minSelectedDate && date.dayOfWeek in selectedDays && !isInRangeMode
}

private fun computeIsFirstInRange(
    date: LocalDate,
    minSelectedDate: LocalDate?,
    maxSelectedDate: LocalDate?,
    isInRangeMode: Boolean,
): Boolean = isInRangeMode && date == minSelectedDate && date != maxSelectedDate

private fun computeIsLastInRange(
    date: LocalDate,
    minSelectedDate: LocalDate?,
    maxSelectedDate: LocalDate?,
    isInRangeMode: Boolean,
): Boolean = isInRangeMode && date == maxSelectedDate && date != minSelectedDate

private fun computeIsInRange(
    date: LocalDate,
    minSelectedDate: LocalDate?,
    maxSelectedDate: LocalDate?,
    isInRangeMode: Boolean,
    isEnabled: Boolean,
): Boolean {
    minSelectedDate ?: return false
    maxSelectedDate ?: return false

    return isInRangeMode && isEnabled && date > minSelectedDate && date < maxSelectedDate
}

private fun computeIsOutOfBounds(
    date: LocalDate,
    monthOfGrid: Month
): Boolean = date.month != monthOfGrid

