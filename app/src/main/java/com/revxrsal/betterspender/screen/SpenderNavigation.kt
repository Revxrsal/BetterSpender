package com.revxrsal.betterspender.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.revxrsal.betterspender.screen.home.HomeScreen
import com.revxrsal.betterspender.screen.settings.SettingsScreen
import com.revxrsal.betterspender.screen.stats.StatsScreen
import com.revxrsal.betterspender.util.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpenderNavigation() {
    val navigator = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navigator) {
        NavHost(navController = navigator, startDestination = "home") {
            composable("home") {
                HomeScreen()
            }
            composable("settings") {
                SettingsScreen()
            }
            composable("stats") {
                StatsScreen()
            }
        }
    }
}