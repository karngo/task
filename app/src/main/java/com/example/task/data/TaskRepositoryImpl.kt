package com.example.task.data

import com.example.task.data.database.AppDatabase
import com.example.task.data.database.entity.Task
import com.example.task.data.network.ApiService
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor (
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : TaskRepository {

    override suspend fun getAllTasks(): List<Task> {
        val savedTasks = appDatabase.getTaskDao().getAllTasks()

        if (savedTasks.isNotEmpty())
            return savedTasks

        val fetchedTasks =
            apiService.fetchTasks().body()?.todos?.map { ModelMapper.mapTask(it) } ?: emptyList()
        appDatabase.getTaskDao().insertAll(*fetchedTasks.toTypedArray())

        return fetchedTasks

        return emptyList()
    }

    override suspend fun addTask(task: Task) {
        TODO("Not yet implemented")
    }
}