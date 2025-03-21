package com.example.task

import com.example.task.data.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Dependency {
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}