package com.revxrsal.betterspender.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.revxrsal.betterspender.currency.Currency
import com.revxrsal.betterspender.currency.currencies
import com.revxrsal.betterspender.viewmodel.PreferencesViewModel

@Composable
fun CurrencyName() {
    var display by remember { mutableStateOf(false) }
    val pref = hiltViewModel<PreferencesViewModel>()
    val currency by pref.currency.collectAsState()
    val style = LocalTextStyle.current
    val color = LocalContentColor.current
    TextButton(onClick = { display = true }) {
        Text(
            text = "${currency.currencyName} (${currency.symbol})",
            style = style,
            color = color
        )
    }
    if (display) {
        ChooseCurrency(
            onDismissRequest = { display = false }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChooseCurrency(onDismissRequest: () -> Unit) {
    val pref = hiltViewModel<PreferencesViewModel>()
    val context = LocalContext.current
    val currency by pref.currency.collectAsState()
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismissRequest
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            CurrenciesList(
                selected = currency,
                onCurrencySelected = { pref.setCurrency(context, it) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrenciesList(
    selected: Currency,
    onCurrencySelected: (Currency) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (currency in currencies.values) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 7.5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${currency.currencyName} (${currency.symbol})",
                )
                RadioButton(
                    selected = selected == currency,
                    onClick = { onCurrencySelected(currency) }
                )
            }
        }
    }
}