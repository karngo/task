package com.example.task.data

import com.example.task.data.database.entity.Task

interface TaskRepository {
    suspend fun getAllTasks(): List<Task>
    suspend fun addTask(task: Task)
}