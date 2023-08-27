package com.revxrsal.betterspender.screen.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.revxrsal.betterspender.R
import com.revxrsal.betterspender.component.FloatCounter
import com.revxrsal.betterspender.viewmodel.PreferencesViewModel

@Composable
fun SetSpendingGoal(
    pref: PreferencesViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 20.dp
            ),
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bullseye_arrow),
                    contentDescription = "Set a spending goal",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                )
                Text(
                    text = "Set a spending goal",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                val context = LocalContext.current
                val spendingGoal by pref.spendingGoal.collectAsState()
                var value by remember {
                    mutableStateOf(spendingGoal.coerceAtLeast(1f))
                }
                FloatCounter(
                    value = value,
                    onValueChange = { value = it },
                    modifier = Modifier.padding(10.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FilledTonalButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            pref.setSpendingGoal(context, value)
                            onDismissRequest()
                        }
                    ) {
                        Text("Set")
                    }
                }
            }
        }
    }
}