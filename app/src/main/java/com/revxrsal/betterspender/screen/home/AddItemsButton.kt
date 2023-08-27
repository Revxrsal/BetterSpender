package com.revxrsal.betterspender.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.revxrsal.betterspender.data.entity.ItemPurchase
import com.revxrsal.betterspender.util.rememberItemState
import java.time.LocalDate

@Composable
fun AddItems(
    date: LocalDate,
    itemAdded: (ItemPurchase) -> Unit
) {
    var display by remember { mutableStateOf(false) }
    TextButton(
        onClick = { display = true },
        modifier = Modifier.padding(vertical = 7.5.dp, horizontal = 5.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.AddCircle,
            contentDescription = "Add",
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        Text(
            text = "Add items",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 5.dp),
            fontSize = 15.sp
        )
    }
    if (display) {
        EditItemDialog(
            state = rememberItemState(purchasedAt = date),
            onDismissRequest = { display = false },
            onConfirm = itemAdded
        )
    }
}