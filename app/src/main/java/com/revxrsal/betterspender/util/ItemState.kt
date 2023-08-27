package com.revxrsal.betterspender.util

import androidx.compose.runtime.*
import com.revxrsal.betterspender.component.asText
import com.revxrsal.betterspender.data.entity.ItemPurchase
import java.time.LocalDate

class ItemState(
    name: String,
    price: Float,
    quantity: Float,
    icon: String,
    checked: Boolean,
    val purchasedAt: LocalDate,
    var id: Int?
) {

    var name by mutableStateOf(name)
    var price by mutableStateOf(price)
    var quantity by mutableStateOf(quantity)
    var icon by mutableStateOf(icon)
    var checked by mutableStateOf(checked)

    val item
        get() = ItemPurchase(name, price, quantity, icon, checked, purchasedAt).also {
            it.id = id
        }

}

@Composable
fun rememberItemState(item: ItemPurchase) = remember {
    ItemState(
        item.name,
        item.price,
        item.quantity,
        item.icon,
        item.checked,
        item.purchasedAt,
        item.id
    )
}

@Composable
fun rememberItemState(
    name: String = "",
    price: Float = 1f,
    quantity: Float = 1f,
    icon: String = icons.random().first.asText(),
    checked: Boolean = false,
    purchasedAt: LocalDate = LocalDate.now(),
    id: Int? = null
) = remember {
    ItemState(
        name = name,
        price = price,
        quantity = quantity,
        icon = icon,
        checked = checked,
        purchasedAt = purchasedAt,
        id = id
    )
}