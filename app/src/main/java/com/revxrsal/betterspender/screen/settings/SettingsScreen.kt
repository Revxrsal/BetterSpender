package com.revxrsal.betterspender.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.revxrsal.betterspender.util.LocalNavController
import com.revxrsal.betterspender.viewmodel.PreferencesViewModel

@Composable
private fun Setting(
    label: String,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        content()
    }
}

@Composable
fun SettingsScreen() {
    val navigator = LocalNavController.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navigator.navigateUp() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }
        Text(
            text = "Settings",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.padding(25.dp),
            textAlign = TextAlign.Center
        )
        Setting(label = "Currency") {
            CurrencyName()
        }
        Setting(label = "Dark theme") {
            SwitchTheme()
        }
        Setting(label = "Hide checked items") {
            HideCheckedItems()
        }
    }
}

@Composable
private fun SwitchTheme() {
    val pref = hiltViewModel<PreferencesViewModel>()
    val hide by pref.darkTheme.collectAsState()
    var checked by remember { mutableStateOf(hide) }
    val context = LocalContext.current
    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
            pref.setDarkTheme(context, it)
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HideCheckedItems() {
    val pref = hiltViewModel<PreferencesViewModel>()
    val hide by pref.hideCheckedItems.collectAsState()
    var checked by remember { mutableStateOf(hide) }
    val context = LocalContext.current
    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
            pref.setHideCheckedItems(context, it)
        }
    )
}