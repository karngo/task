package com.example.task.data

import com.example.task.data.database.entity.Task
import com.example.task.data.network.model.Task as ApiTask

object ModelMapper {
    fun mapTask(apiTask: ApiTask): Task {
        return Task(
            id = apiTask.id,
            todo = apiTask.todo,
            isCompleted = apiTask.completed
        )
    }
}