package com.example.fetch_list_app.repository

import com.example.fetch_list_app.data.Item
import com.example.fetch_list_app.network.RetrofitInstance

class ItemRepository {
    suspend fun fetchItems(): List<Item> {
        return RetrofitInstance.api.getItems()
    }
}