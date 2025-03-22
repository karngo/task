package com.example.task.analytics

interface AppAnalytics {
    fun logEvent(event: AppEvent)
}