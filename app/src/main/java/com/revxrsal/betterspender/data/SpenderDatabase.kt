package com.revxrsal.betterspender.data

import androidx.room.BuiltInTypeConverters
import androidx.room.BuiltInTypeConverters.State.ENABLED
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.revxrsal.betterspender.data.dao.ItemsDao
import com.revxrsal.betterspender.data.entity.ItemPurchase

@Database(
    entities = [ItemPurchase::class],
    exportSchema = false,
    version = 4
)
@TypeConverters(
    Converters::class,
    builtInTypeConverters = BuiltInTypeConverters(
        enums = ENABLED,
        uuid = ENABLED
    )
)
abstract class SpenderDatabase : RoomDatabase() {

    abstract fun items(): ItemsDao

}