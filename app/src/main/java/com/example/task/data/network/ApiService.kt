package com.example.task.data.network

import com.example.task.data.network.model.TaskResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("todos")
    suspend fun fetchTasks(@Query("limit") limit: Int = 5): Response<TaskResponse>
}
