package com.revxrsal.betterspender.screen.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.revxrsal.betterspender.component.LinearCalendar
import com.revxrsal.betterspender.component.Today
import com.revxrsal.betterspender.viewmodel.DatabaseViewModel
import com.revxrsal.betterspender.viewmodel.PreferencesViewModel

@Composable
fun HomeScreen(
    database: DatabaseViewModel = hiltViewModel(),
    pref: PreferencesViewModel = hiltViewModel()
) {
    var date by remember { mutableStateOf(Today) }
    val state = rememberLazyListState(80)
    val items by database.items.collectAsState()
    val hideCheckedItems by pref.hideCheckedItems.collectAsState()
    val dayItems = remember(date, items) {
        items.filter {
            it.purchasedAt == date && (!hideCheckedItems || !it.checked)
        }
    }
    LaunchedEffect(Unit) {
        state.animateScrollToItem(90 - (state.layoutInfo.visibleItemsInfo.size / 2) + 1)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            TopAppBar()
            LinearCalendar(
                state = state,
                startFrom = Today.minusMonths(3),
                selected = date,
                onSelected = { date = it }
            )
            AddItems(
                date = date,
                itemAdded = { database.add(it) }
            )
            Crossfade(targetState = dayItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = TopCenter
                ) {
                    if (it)
                        NoPurchases()
                    else
                        ListItems(
                            items = dayItems,
                            onItemUpdated = { database.update(it) }
                        )
                }
            }
        }
    }
}
