package com.revxrsal.betterspender.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.revxrsal.betterspender.R.drawable.wallet

@Composable
fun NoPurchases() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Text(
            text = "Nothing to see here.",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        Image(
            painter = painterResource(wallet),
            contentDescription = "Nothing here",
            modifier = Modifier.size(128.dp)
        )
    }
}