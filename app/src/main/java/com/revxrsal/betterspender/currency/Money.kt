package com.revxrsal.betterspender.currency

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.revxrsal.betterspender.viewmodel.PreferencesViewModel

val currencies = Currency.values().associateBy { it.name }

val String.currency get() = currencies[this]

enum class Currency(
    val currencyName: String,
    val symbol: String,
    val beforeNumber: Boolean = symbol.length == 1
) {
    JOD("Jordanian Dinar", "JD"),
    USD("United States Dollar", "$"),
    POUND("Pound Sterling", "£"),
    LEK("Albania Lek", "Lek"),
    RUPEE("Rupee", "Rs"),
    SHILLING("Somalia Shilling", "S"),
    SAUDI_RIYAL("Saudi Riyal", "SR"),
    OMAN_RIYAL("Oman Riyal", "OMR"),
    EGYPTIAN("Egypt Pound", "EGP"),
    DIRHAM("UAE Dirham", "AED"),
    DENMARK("Denmark Krone", "DKK")
}

fun Currency.format(num: String): String {
    return if (beforeNumber) "$symbol$num"
    else "$num $symbol"
}

@Composable
fun money(value: Float): String {
    val pref: PreferencesViewModel = hiltViewModel()
    val currency by pref.currency.collectAsState()
    return if (value % 1 == 0f)
        currency.format(value.toInt().toString())
    else
        currency.format("%.1f".format(value))
}
