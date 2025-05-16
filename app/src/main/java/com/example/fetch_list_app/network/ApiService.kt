package com.example.fetch_list_app.network

import com.example.fetch_list_app.data.Item
import retrofit2.http.GET

interface ApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}