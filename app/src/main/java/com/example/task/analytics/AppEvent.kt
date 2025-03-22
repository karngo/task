package com.example.task.analytics

data class AppEvent(
    val name: String,
    val properties: Map<String, String> = emptyMap()
)
