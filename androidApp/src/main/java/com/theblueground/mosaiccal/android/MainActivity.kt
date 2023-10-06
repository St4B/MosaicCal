package com.theblueground.mosaiccal.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.theblueground.mosaiccal.ui.DisplayStyle
import com.theblueground.mosaiccal.ui.MosaicCal
import com.theblueground.mosaiccal.ui.theme.MosaicCalDayCellStyle
import com.theblueground.mosaiccal.ui.theme.MosaicCalDayHighlightStyle
import com.theblueground.mosaiccal.ui.theme.MosaicCalDayTitleStyle
import com.theblueground.mosaiccal.ui.theme.MosaicCalMonthHeaderStyle
import com.theblueground.mosaiccal.ui.theme.MosaicCalStyle
import com.theblueground.mosaiccal.ui.theme.MosaicCalThemeColors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

private val lightColors = MosaicCalThemeColors(
    dayHighlightColor = Color(0xFF00BCD4),
    dayCellSelectedBorderColor = Color(0xFF3F51B5),
    rangeLimitColor = Color(0xFF3F51B5),
    rangeBackgroundColor = Color(0xFF2196F3),
)

private val darkColors = MosaicCalThemeColors(
    dayHighlightColor = Color(0xFFCDDC39),
    dayCellSelectedBorderColor = Color(0xFF009688),
    rangeLimitColor = Color(0xFF009688),
    rangeBackgroundColor = Color(0xFF4CAF50),
)

private val style = MosaicCalStyle(
    dayTitleStyle = MosaicCalDayTitleStyle(
        dayTitleAlignment = Alignment.CenterStart,
        dayTitleNameStyle = DisplayStyle.SHORT_STANDALONE,
    ),
    monthHeaderStyle = MosaicCalMonthHeaderStyle(
        monthHeaderTopPadding = 32.dp,
        monthHeaderStartPadding = 16.dp,
        monthHeaderBottomPadding = 16.dp,
        monthHeaderEndPadding = 16.dp,
        monthHeaderNameStyle = DisplayStyle.SHORT_STANDALONE,
    ),
    dayHighlightStyle = MosaicCalDayHighlightStyle(
        dayHighlightAlignment = Alignment.TopEnd,
        dayHighlightSize = 16.dp,
        dayHighlightShape = CutCornerShape(32.dp, 8.dp, 32.dp, 8.dp),
    ),
    dayCellStyle = MosaicCalDayCellStyle(
        dayCellPadding = 16.dp,
        dayCellBorder = 4.dp,
        dayCellShape = CutCornerShape(16.dp, 16.dp, 32.dp, 16.dp),
        dayCellTextSize = 48.dp,
        dayCellDisabledAlpha = 0.8f,
    )
)


@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel(),
) {
    val colors = if (isSystemInDarkTheme()) darkColors else lightColors
    val state = mainViewModel.state.collectAsState()

    Column {
        Button(
            onClick = { mainViewModel.processIntent(intent = MainIntent.ToggleMode) }
        ) {
            Text(text = "Toggle mode")
        }

        MosaicCal(
            daysOfWeek = state.value.daysOfWeek,
            months = state.value.months,
            onSelectDate = { mainViewModel.processIntent(intent = MainIntent.SelectDate(it)) },
            colors = colors,
            style = style,
        )
    }

    LaunchedEffect(true) {
        mainViewModel.processIntent(intent = MainIntent.LoadData)
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        MainScreen()
    }
}
