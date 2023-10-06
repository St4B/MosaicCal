package com.theblueground.mosaiccal.core

import kotlinx.datetime.LocalDate

interface BaseDayItem {

    val date: LocalDate

    val isEnabled: Boolean

    val isSelected: Boolean
}