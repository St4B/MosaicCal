package com.theblueground.mosaiccal.android

import kotlinx.datetime.LocalDate

sealed class MainIntent {

    object LoadData : MainIntent()

    object ToggleMode : MainIntent()

    data class SelectDate(val date: LocalDate) : MainIntent()
}