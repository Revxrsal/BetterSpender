package com.revxrsal.betterspender.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.revxrsal.betterspender.viewmodel.PreferencesViewModel
import java.util.concurrent.atomic.AtomicInteger

val Material600 = listOf(
    Color(192, 202, 51, 255),
    Color(229, 57, 53, 255),
    Color(57, 73, 171, 255),
    Color(30, 136, 229, 255),
    Color(67, 160, 71, 255),
    Color(142, 36, 170, 255),
    Color(244, 81, 30, 255),
    Color(0, 137, 123, 255),
    Color(3, 155, 229, 255),
    Color(251, 140, 0, 255),
)

val Material700 = listOf(
    Color(197, 17, 98, 255),
    Color(41, 98, 255, 255),
    Color(238, 255, 65, 255),
    Color(116, 197, 185, 255),
    Color(255, 23, 68, 255),
    Color(117, 125, 179, 255),
    Color(255, 110, 64, 255),
    Color(199, 124, 207, 255),
    Color(0, 191, 165, 255),
    Color(124, 179, 66, 255),
    Color(255, 82, 82, 255),
    Color(118, 204, 150, 255),
)

@Composable
fun colorGen(): ColorGen {
    val pref: PreferencesViewModel = hiltViewModel()
    val darkTheme by pref.darkTheme.collectAsState()
    return if (darkTheme) ColorGen(Material700)
    else ColorGen(Material600)
}

@Composable
fun progressIndicatorColors(): Pair<Color, Color> {
    val pref: PreferencesViewModel = hiltViewModel()
    val darkTheme by pref.darkTheme.collectAsState()
    return if (darkTheme)
        Color(1, 1, 1) to Color(1, 1, 1)
    else
        Color(1, 1, 1) to Color(1, 1, 1)
}

class ColorGen(private val colors: List<Color>) {

    private val counter = AtomicInteger(0)
    val next: Color get() = colors[counter.getAndIncrement() % colors.size]

}