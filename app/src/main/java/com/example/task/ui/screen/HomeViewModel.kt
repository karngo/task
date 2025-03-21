package com.example.task.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.Dependency
import com.example.task.data.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    fun getTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            _tasks.value = Dependency.apiService.fetchTasks().body()?.todos ?: emptyList()
        }
    }
}