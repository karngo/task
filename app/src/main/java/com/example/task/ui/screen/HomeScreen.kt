package com.example.task.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.task.analytics.AppEvent
import com.example.task.data.database.entity.Task
import com.example.task.ui.theme.TaskTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier,
    homeViewModel: HomeViewModel = viewModel(),
    logEvent: (AppEvent) -> Unit
) {
    val tasks by homeViewModel.tasks.collectAsState()
    var isDialogVisible by remember { mutableStateOf(false) }
    val composableScope = rememberCoroutineScope()

    var activeTask by remember { mutableStateOf(Task(todo = "")) }
    var isEditing by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        homeViewModel.getTasks()
    }

    Column(modifier) {
        TextButton(onClick = {
            activeTask = Task(todo = "")
            isEditing = false
            isDialogVisible = true
        }) {
            Text(text = "+ Add new task")
        }

        LazyColumn {
            items(tasks) { task ->
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = task.isCompleted, onCheckedChange = {
                        composableScope.launch {
                            if (!task.isCompleted)
                                logEvent(AppEvent("task_completed"))

                            homeViewModel.toggleTaskCompleted(task)
                        }
                    })
                    Text(
                        text = task.todo ?: "",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        activeTask = task
                        isEditing = true
                        isDialogVisible = true
                    }) {
                        Icon(imageVector = Icons.Filled.Create, contentDescription = "Edit task")
                    }
                }
                HorizontalDivider(thickness = 1.dp)
            }
        }
    }

    if (isDialogVisible)
        EnterTaskDialog(activeTask, onDismiss = { isDialogVisible = false }) {
            isDialogVisible = false
            composableScope.launch {
                if (isEditing) {
                    homeViewModel.updateTask(it)
                    logEvent(AppEvent("task_edited"))
                } else {
                    homeViewModel.addTask(it)
                    logEvent(AppEvent("task_added"))
                }
            }
        }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TaskTheme {
        HomeScreen(modifier = Modifier, logEvent = {})
    }
}

@Composable
fun EnterTaskDialog(task: Task, onDismiss: () -> Unit, onSave: (task: Task) -> Unit) {
    var taskText by remember { mutableStateOf(task.todo) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(text = "Enter your task...")
                    },
                    value = taskText,
                    onValueChange = { taskText = it })
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(onClick = onDismiss) { Text("Cancel") }
                    TextButton(onClick = { onSave(task.copy(todo = taskText)) }) { Text("Confirm") }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EnterTaskDialogPreview() {
    TaskTheme {
        EnterTaskDialog(Task(todo = "Enter new task..."), onSave = {}, onDismiss = {})
    }
}

