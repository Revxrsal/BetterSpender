package com.revxrsal.betterspender

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.revxrsal.betterspender.screen.SpenderNavigation
import com.revxrsal.betterspender.ui.theme.BetterSpenderTheme
import com.revxrsal.betterspender.viewmodel.PreferencesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val pref = hiltViewModel<PreferencesViewModel>()
            val darkTheme by pref.darkTheme.collectAsState()
            BetterSpenderTheme(darkTheme = darkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SpenderNavigation()
                }
            }
        }
    }
}
