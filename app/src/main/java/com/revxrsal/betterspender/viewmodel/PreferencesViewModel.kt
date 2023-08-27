package com.revxrsal.betterspender.viewmodel

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revxrsal.betterspender.currency.Currency
import com.revxrsal.betterspender.currency.currency
import com.revxrsal.betterspender.util.icons
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "betterspender")

val DARK_THEME = booleanPreferencesKey("darkTheme")
val HIDE_CHECKED_ITEMS = booleanPreferencesKey("hideCheckedItems")
val SPENDING_GOAL = floatPreferencesKey("spendingGoal")
val CURRENCY = stringPreferencesKey("currency")

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {

    val darkTheme = context.dataStore.data
        .map { it[DARK_THEME] ?: true }
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    val spendingGoal = context.dataStore.data
        .map { it[SPENDING_GOAL] ?: 0f }
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.Lazily, 0f)

    val hideCheckedItems = context.dataStore.data
        .map { it[HIDE_CHECKED_ITEMS] ?: false }
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    val currency = context.dataStore.data
        .map { it[CURRENCY]?.currency ?: Currency.JOD }
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.Lazily, Currency.JOD)

    fun setDarkTheme(context: Context, dark: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            context.dataStore.edit { it[DARK_THEME] = dark }
        }
    }

    fun setSpendingGoal(context: Context, goal: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            context.dataStore.edit { it[SPENDING_GOAL] = goal }
        }
    }

    fun setCurrency(context: Context, currency: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            context.dataStore.edit { it[CURRENCY] = currency.name }
        }
    }

    fun setHideCheckedItems(context: Context, hide: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            context.dataStore.edit { it[HIDE_CHECKED_ITEMS] = hide }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) { icons.size }
    }

}