package com.revxrsal.betterspender.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.guru.fontawesomecomposelib.FaIcons
import com.revxrsal.betterspender.component.FaIcon
import com.revxrsal.betterspender.component.FloatCounter
import com.revxrsal.betterspender.component.IconPicker
import com.revxrsal.betterspender.component.asText
import com.revxrsal.betterspender.data.entity.ItemPurchase
import com.revxrsal.betterspender.util.ItemState
import com.revxrsal.betterspender.util.rememberItemState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditItemDialog(
    title: String = "Add item",
    state: ItemState = rememberItemState(),
    displayDelete: Boolean = false,
    onDismissRequest: () -> Unit,
    onConfirm: (ItemPurchase) -> Unit,
    onDelete: (ItemState) -> Unit = {}
) {
    var displayIconPicker by remember { mutableStateOf(false) }
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismissRequest
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                horizontalAlignment = CenterHorizontally
            ) {
                TopBar(
                    onDismissRequest = onDismissRequest,
                    checkEnabled = state.name.isNotBlank(),
                    displayDelete = displayDelete,
                    onCheck = {
                        onConfirm(state.item)
                        onDismissRequest()
                    },
                    onDelete = { onDelete(state) },
                    title = title,
                )
                OutlinedTextField(
                    value = state.name,
                    onValueChange = { state.name = it },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    label = { Text(text = "Item") }
                )
                LabeledItem("Price") {
                    FloatCounter(
                        value = state.price,
                        onValueChange = { state.price = it }
                    )
                }
                LabeledItem("Quantity") {
                    FloatCounter(
                        value = state.quantity,
                        onValueChange = { state.quantity = it }
                    )
                }
                LabeledItem("Icon") {
                    IconButton(onClick = { displayIconPicker = true }) {
                        FaIcon(
                            faIcon = state.icon ?: FaIcons.BorderNone.asText()
                        )
                    }
                }
            }
        }
    }
    if (displayIconPicker) {
        IconPickerDialog(
            selected = state.icon,
            onIconSelected = { state.icon = it },
            onDismissRequest = { displayIconPicker = false },
        )
    }
}

@Composable
private fun LabeledItem(
    label: String,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold
        )
        content()
    }
}

@Composable
private fun IconPickerDialog(
    selected: String,
    onIconSelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Column(
                horizontalAlignment = CenterHorizontally
            ) {
                Text(
                    text = "Pick an icon",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                var query by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.padding(10.dp),
                    label = { Text("Search icons") }
                )
                IconPicker(
                    modifier = Modifier
                        .padding(10.dp)
                        .heightIn(max = 250.dp),
                    selected = selected,
                    filter = query.ifEmpty { null },
                    onIconSelected = {
                        onIconSelected(it)
                        onDismissRequest()
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = onDismissRequest) {
                        Text("Done")
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    onDismissRequest: () -> Unit,
    checkEnabled: Boolean,
    displayDelete: Boolean,
    onCheck: () -> Unit,
    onDelete: () -> Unit,
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onDismissRequest) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Cancel")
        }
        if (displayDelete)
        IconButton(onClick = onDelete) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Add")
        }
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        IconButton(onClick = onCheck, enabled = checkEnabled) {
            Icon(imageVector = Icons.Filled.Check, contentDescription = "Add")
        }
    }
}
