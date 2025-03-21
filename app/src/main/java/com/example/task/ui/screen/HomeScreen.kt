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
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.task.ui.theme.TaskTheme

@Composable
fun HomeScreen(modifier: Modifier, homeViewModel: HomeViewModel = viewModel()) {
    val tasks by homeViewModel.tasks.collectAsState()
    var isDialogVisible by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        homeViewModel.getTasks()
    }

    Column(modifier) {
        TextButton(onClick = { isDialogVisible = true }) {
            Text(text = "+ Add new task")
        }

        LazyColumn {
            items(tasks) { article ->
                Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp)) {
                    Text(text = article.todo ?: "", fontWeight = FontWeight.Bold)
                }
                HorizontalDivider(thickness = 1.dp)
            }
        }
    }

    if (isDialogVisible)
        AddTaskDialog(text = "", onDismiss = { isDialogVisible = false }) {
            isDialogVisible = false
        }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TaskTheme {
        HomeScreen(modifier = Modifier)
    }
}

@Composable
fun AddTaskDialog(text: String, onDismiss: () -> Unit, onSave: (newText: String) -> Unit) {
    var taskText by remember { mutableStateOf(text) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(text = "Enter new task...")
                    },
                    value = taskText,
                    onValueChange = { taskText = it })
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(onClick = onDismiss) { Text("Cancel") }
                    TextButton(onClick = { onSave(taskText) }) { Text("Confirm") }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddTaskDialogPreview() {
    TaskTheme {
        AddTaskDialog(text = "Enter new task...", onSave = {}, onDismiss = {})
    }
}

