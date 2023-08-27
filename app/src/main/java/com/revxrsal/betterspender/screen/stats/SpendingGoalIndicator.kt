package com.revxrsal.betterspender.screen.stats

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.revxrsal.betterspender.currency.money
import com.revxrsal.betterspender.viewmodel.PreferencesViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SpendingGoalIndicator(
    sum: Float,
    pref: PreferencesViewModel = hiltViewModel()
) {
    val spendingGoal by pref.spendingGoal.collectAsState()
    SetGoalButton()
    if (spendingGoal != 0f) {
        Text(
            text = "Spending Goal",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 25.dp, bottom = 10.dp)
        )

        Crossfade(targetState = spendingGoal) {
            if (sum > it)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = money(sum - it),
                        fontSize = 22.sp,
                        color = Color(213, 45, 45, 255),
                        modifier = Modifier.padding(5.dp)
                    )
                    Text(
                        text = "Over goal",
                        modifier = Modifier.padding(5.dp)
                    )
                }
            else
                Indicator(value = sum / it, rem = it - sum)
        }
    }
}

@Composable
private fun Indicator(value: Float, rem: Float) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = money(rem),
            fontSize = 22.sp,
            color = Color(47, 129, 81, 255),
            modifier = Modifier.padding(10.dp)
        )
        Text(
            text = "Remaining",
            modifier = Modifier.padding(10.dp)
        )
        LinearProgressIndicator(
            progress = value,
            modifier = Modifier
                .padding(10.dp)
                .heightIn(min = 25.dp)
                .clip(shape = CircleShape)
        )
    }
}

@Composable
private fun SetGoalButton() {
    var displaySetGoal by remember { mutableStateOf(false) }
    TextButton(
        onClick = { displaySetGoal = true },
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = "Set a spending goal",
            fontSize = 15.sp
        )
    }
    if (displaySetGoal) {
        SetSpendingGoal(
            onDismissRequest = { displaySetGoal = false }
        )
    }
}
