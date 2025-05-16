package com.example.fetch_list_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetch_list_app.data.Item
import com.example.fetch_list_app.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ItemGroup(val listId: Int, val items: List<Item>)

class MainViewModel : ViewModel() {

    private val repository = ItemRepository()

    private val _groups = MutableStateFlow<List<ItemGroup>>(emptyList())
    val groups: StateFlow<List<ItemGroup>> = _groups

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            try {
                val items = repository.fetchItems()
                val filtered = items.filter { !it.name.isNullOrBlank() }
                val sorted = filtered.sortedWith(compareBy({ it.listId }, { it.name }))
                val grouped = sorted.groupBy { it.listId }
                    .map { (listId, items) -> ItemGroup(listId, items) }
                _groups.value = grouped
            } catch (e: Exception) {
                _error.value = "Error loading items: ${e.message}"
            }
        }
    }
}