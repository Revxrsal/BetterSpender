package com.revxrsal.betterspender.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "purchases")
class ItemPurchase(
    val name: String,
    val price: Float,
    val quantity: Float,
    val icon: String,
    val checked: Boolean = false,
    val purchasedAt: LocalDate = LocalDate.now()
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}