package com.revxrsal.betterspender.data.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.revxrsal.betterspender.data.entity.ItemPurchase
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {

    @Insert(onConflict = REPLACE)
    fun add(item: ItemPurchase)

    @Update
    fun update(item: ItemPurchase)

    @Delete
    fun delete(item: ItemPurchase)

    @Query("select * from purchases where id = :id limit 1")
    fun get(id: Int): ItemPurchase?

    @Query("select * from purchases")
    fun getAll(): Flow<List<ItemPurchase>>

}