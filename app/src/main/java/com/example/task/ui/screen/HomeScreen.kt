package com.example.task.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.task.ui.theme.TaskTheme

@Composable
fun HomeScreen(modifier: Modifier) {
    val homeViewModel = HomeViewModel()
    val tasks by homeViewModel.tasks.collectAsState()

    LaunchedEffect(true) {
        homeViewModel.getTasks()
    }

    LazyColumn(modifier = modifier.padding(12.dp)) {
        items(tasks) { article ->
            Column(
                modifier = Modifier
                    .border(BorderStroke(1.dp, SolidColor(Color.Blue)))
                    .padding(12.dp)
            ) {
                Text(text = article.todo ?: "", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskTheme {
        HomeScreen(modifier = Modifier)
    }
}