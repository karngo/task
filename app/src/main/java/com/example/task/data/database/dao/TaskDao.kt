package com.example.task.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.task.data.database.entity.Task

@Dao
interface TaskDao {
    @Insert
    fun insertAll(vararg tasks: Task)

    @Query("Select * From tasks")
    suspend fun getAllTasks(): List<Task>

    @Insert
    fun insertTask(task: Task)

    @Update
    fun updateTask(task: Task)
}