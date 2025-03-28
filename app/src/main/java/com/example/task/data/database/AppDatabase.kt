package com.example.task.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task.data.database.dao.TaskDao
import com.example.task.data.database.entity.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
}