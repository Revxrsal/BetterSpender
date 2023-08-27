package com.revxrsal.betterspender.screen.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.revxrsal.betterspender.R.drawable.dark_mode
import com.revxrsal.betterspender.R.drawable.light_mode
import com.revxrsal.betterspender.util.LocalNavController
import com.revxrsal.betterspender.viewmodel.PreferencesViewModel

@Composable
fun TopAppBar() {
    Box(
        modifier = Modifier
//        .fillMaxSize()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SwitchThemeButton()
            SidebarMenuIcon()
        }
    }
}

@Composable
private fun SidebarMenuIcon() {
    val navigator = LocalNavController.current
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        IconButton(
            onClick = { expanded = true },
            Modifier.align(Alignment.TopEnd)
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                DropdownMenuItem(
                    onClick = { navigator.navigate("stats") },
                    text = {
                        Text(
                            text = "Statistics",
                            fontSize = 16.sp
                        )
                    }
                )
                DropdownMenuItem(
                    onClick = { navigator.navigate("settings") },
                    text = {
                        Text(
                            text = "Settings",
                            fontSize = 16.sp
                        )
                    }
                )
            }
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "Settings",
                modifier = Modifier.align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
private fun SwitchThemeButton() {
    val context = LocalContext.current
    val pref = hiltViewModel<PreferencesViewModel>()
    val darkTheme by pref.darkTheme.collectAsState()
    val icon = remember(darkTheme) { if (darkTheme) light_mode else dark_mode }
    IconButton(
        onClick = { pref.setDarkTheme(context = context, dark = !darkTheme) }
    ) {
        Crossfade(targetState = icon) {
            Icon(painterResource(it), "Toggle theme")
        }
    }
}
