package com.theblueground.mosaiccal.core

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitDay
import platform.Foundation.NSCalendarUnitMonth
import platform.Foundation.NSCalendarUnitYear
import platform.Foundation.NSDate

fun NSDate.toLocalDate(): LocalDate {
    val year = NSCalendar.currentCalendar.component(NSCalendarUnitYear, this).toInt()
    val month = NSCalendar.currentCalendar.component(NSCalendarUnitMonth, this).toInt()
    val day = NSCalendar.currentCalendar.component(NSCalendarUnitDay, this).toInt()

    return LocalDate(year = year, monthNumber = month, dayOfMonth = day)
}

fun LocalDate.toDayOfWeek(): DayOfWeek = dayOfWeek

fun LocalDate.plusYears(years: Int): LocalDate = plus(years,  DateTimeUnit.YEAR)

fun LocalDate.plusMonths(months: Int): LocalDate = plus(months,  DateTimeUnit.MONTH)

fun LocalDate.plusDays(days: Int): LocalDate = plus(days,  DateTimeUnit.DAY)

fun LocalDate.minusYears(years: Int): LocalDate = minus(years,  DateTimeUnit.YEAR)

fun LocalDate.minusMonths(months: Int): LocalDate = minus(months,  DateTimeUnit.MONTH)

fun LocalDate.minusDays(days: Int): LocalDate = minus(days,  DateTimeUnit.DAY)

fun Set<LocalDate>.minimum(): LocalDate? = this.minOrNull()

fun Set<LocalDate>.maximum(): LocalDate? = this.maxOrNull()