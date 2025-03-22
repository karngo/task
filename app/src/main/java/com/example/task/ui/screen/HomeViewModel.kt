package com.example.task.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.data.TaskRepository
import com.example.task.data.database.entity.Task
import com.example.task.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    fun getTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            _tasks.value = taskRepository.getAllTasks()
        }
    }
}