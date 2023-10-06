package com.theblueground.mosaiccal.core

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.daysUntil
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.monthsUntil
import kotlinx.datetime.plus

class MosaicDataProvider {

    companion object {

        const val DAYS_OF_WEEK = 7
    }

    fun <T : BaseDayItem> provideMonths(
        from: LocalDate,
        to: LocalDate,
        mapper: (LocalDate, Month) -> T,
    ): List<MonthGrid<T>> {
        val firstDayOfFirstMonth = LocalDate(year = from.year, month = from.month, dayOfMonth = 1)

        val lastDayOfLastMonth = LocalDate(
            year = to.year,
            month = to.month,
            dayOfMonth = to.lengthOfMonth()
        )

        val daysOfWeek: List<DayOfWeek> = provideDaysOfWeek()

        val numOfMonthsBetween = firstDayOfFirstMonth.monthsUntil(lastDayOfLastMonth)

        return (0..numOfMonthsBetween).map {
            val monthNumber = ((from.monthNumber + it - 1) % 12 + 1).toInt()
            val yearNumber = (from.year + (from.monthNumber + it) / 12).toInt()

            val firstDayOfMonth = LocalDate(yearNumber, monthNumber, 1)
            val lastDayOfMonth = LocalDate(yearNumber, monthNumber, firstDayOfMonth.lengthOfMonth())

            val firstDayOfGrid = firstDayOfMonth.previousOrSameDayOfWeek(daysOfWeek.first())
            val lastDayOfGrid = lastDayOfMonth.nextOrSameDayOfWeek(daysOfWeek.last())

            val numOfDaysBetween =  firstDayOfGrid.daysUntil(lastDayOfGrid)

            val daysRows = (0..numOfDaysBetween)
                .chunked(DAYS_OF_WEEK)
                .map { daysInWeek ->
                    val dayItemsPerWeek = daysInWeek.map { day ->
                        val date = firstDayOfGrid.plus(day, DateTimeUnit.DAY)
                        mapper(date, firstDayOfMonth.month)
                    }
                    DaysRow(days = dayItemsPerWeek)
                }

            MonthGrid(
                firstDayOfMonth.year,
                month = firstDayOfMonth.month,
                daysRows = daysRows
            )
        }
    }

    fun provideDaysOfWeek(): List<DayOfWeek> = DayOfWeek.values()
        .sortedBy { (it.isoDayNumber - LocaleBasedValues.firstDayOfWeek.isoDayNumber + 7) % 7 }

    private fun LocalDate.previousOrSameDayOfWeek(
        dayOfWeek: DayOfWeek,
    ): LocalDate {
        val daysDiff = dayOfWeek.isoDayNumber - this.dayOfWeek.isoDayNumber
        if (daysDiff == 0) return this

        val daysToAdd = if (daysDiff >= 0) 7 - daysDiff else -daysDiff
        return minus(daysToAdd, DateTimeUnit.DAY)
    }

    private fun LocalDate.nextOrSameDayOfWeek(
        dayOfWeek: DayOfWeek,
    ): LocalDate {
        val daysDiff = this.dayOfWeek.isoDayNumber - dayOfWeek.isoDayNumber
        if (daysDiff == 0) return this

        val daysToAdd = if (daysDiff >= 0) 7 - daysDiff else -daysDiff
        return plus(daysToAdd, DateTimeUnit.DAY)
    }

    private fun LocalDate.lengthOfMonth(): Int = monthNumber.monthLength(isLeapYear(year))

    // This is internal function of kotlinx-datetime library
    private fun isLeapYear(year: Int): Boolean {
        val prolepticYear: Long = year.toLong()
        return prolepticYear and 3 == 0L && (prolepticYear % 100 != 0L || prolepticYear % 400 == 0L)
    }

    // This is internal function of kotlinx-datetime library
    private fun Int.monthLength(isLeapYear: Boolean): Int = when (this) {
        2 -> if (isLeapYear) 29 else 28
        4, 6, 9, 11 -> 30
        else -> 31
    }
}
