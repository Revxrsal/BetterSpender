package com.revxrsal.betterspender.component

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle.SHORT_STANDALONE
import java.util.*

val Today: LocalDate = LocalDate.now()
val Formatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)

@Composable
fun LinearCalendar(
    startFrom: LocalDate = LocalDate.now(),
    state: LazyListState = rememberLazyListState(),
    selected: LocalDate,
    onSelected: (LocalDate) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val todayVisible = state.layoutInfo.visibleItemsInfo.any { it.index == 90 }
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val navToToday = remember { Animatable(0f) }
            LaunchedEffect(todayVisible) {
                navToToday.animateTo(if (!todayVisible) 1f else 0f)
            }
            Crossfade(targetState = selected) {
                Text(
                    text = it.format(Formatter),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            TextButton(
                onClick = {
                    scope.launch {
                        state.animateScrollToItem(90 - (state.layoutInfo.visibleItemsInfo.size / 2) + 1)
                        onSelected(Today)
                    }
                },
                modifier = Modifier.alpha(navToToday.value)
            ) {
                Text(text = "Today", fontWeight = FontWeight.Bold)
            }
        }
        LazyRow(state = state) {
            repeat(180) {
                item {
                    val date = startFrom.plusDays(it.toLong())
                    DayBlock(
                        date = date,
                        selected = date == selected,
                        onSelected = { onSelected(date) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
private fun DayBlock(
    date: LocalDate,
    selected: Boolean,
    onSelected: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val color by animateColorAsState(
        if (selected)
            MaterialTheme.colorScheme.primary
        else
            Color.Transparent
    )
    val textColor by animateColorAsState(
        if (selected)
            MaterialTheme.colorScheme.surface
        else
            MaterialTheme.colorScheme.onSurface
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 5.dp)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) { onSelected() },
            shape = RoundedCornerShape(5.dp),
            containerColor = color,
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = date.dayOfWeek.getDisplayName(
                        SHORT_STANDALONE,
                        Locale.ENGLISH
                    ),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor
                )
                Text(
                    text = date.dayOfMonth.toString(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = textColor
                )
            }
        }
    }
}
