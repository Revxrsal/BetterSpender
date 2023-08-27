package com.revxrsal.betterspender.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revxrsal.betterspender.data.SpenderDatabase
import com.revxrsal.betterspender.data.entity.ItemPurchase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val database: SpenderDatabase
) : ViewModel() {

    val items = database.items().getAll()
        .flowOn(Dispatchers.IO)
        .stateIn(initialValue = emptyList())

    fun add(item: ItemPurchase) = async { database.items().add(item) }
    fun delete(item: ItemPurchase) = async { database.items().delete(item) }
    fun update(item: ItemPurchase) = async { database.items().update(item) }
    fun getItem(id: Int) = async { database.items().get(id) }

    private fun async(v: suspend () -> Unit): Job {
        return viewModelScope.launch(Dispatchers.IO) { v() }
    }

    private fun <T> Flow<T>.stateIn(initialValue: T) = stateIn(viewModelScope, Lazily, initialValue)
}
