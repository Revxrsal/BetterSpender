@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class
)

package com.revxrsal.betterspender.screen.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.revxrsal.betterspender.component.FaIcon
import com.revxrsal.betterspender.data.entity.ItemPurchase
import com.revxrsal.betterspender.util.ItemState
import com.revxrsal.betterspender.util.rememberItemState
import com.revxrsal.betterspender.viewmodel.DatabaseViewModel

@Composable
fun DisplayItem(
    item: ItemState,
    onClick: () -> Unit,
    update: (ItemPurchase) -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(
                vertical = 7.5.dp,
                horizontal = 10.dp
            )
            .fillMaxWidth()
            .clickable { onClick() },
        tonalElevation = 15.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Crossfade(targetState = item.checked) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(5.dp)
                    .alpha(if (it) 0.55f else 1f)
            ) {
                Checkbox(
                    checked = item.checked,
                    onCheckedChange = { c ->
                        item.checked = c
                        update(item.item)
                    },
                    modifier = Modifier.clip(CircleShape)
                )
                FaIcon(
                    faIcon = item.icon,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = item.name,
                    modifier = Modifier
                        .padding(15.dp),
                    textDecoration = if (it)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None,
                )
            }
        }
    }
}

@Composable
fun ListItems(
    items: List<ItemPurchase>,
    onItemUpdated: (ItemPurchase) -> Unit,
) {
    val database = hiltViewModel<DatabaseViewModel>()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Crossfade(
            targetState = items,
            modifier = Modifier.align(CenterHorizontally)
        ) {
            LazyColumn {
                items(items = it, key = { it.id!! }) {
                        var isEditing by remember { mutableStateOf(false) }
                        val state = rememberItemState(item = it)
                        DisplayItem(
                            item = state,
                            onClick = { isEditing = true },
                            update = onItemUpdated
                        )
                        if (isEditing) {
                            EditItemDialog(
                                title = "Edit item",
                                state = state,
                                displayDelete = false,
                                onDismissRequest = { isEditing = false },
                                onConfirm = { item -> onItemUpdated(item) },
                                onDelete = { i ->
                                    database.delete(i.item)
                                }
                            )
                    }
                }
            }
        }
    }
}