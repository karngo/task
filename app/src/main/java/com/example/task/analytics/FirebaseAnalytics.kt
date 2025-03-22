package com.example.task.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class FirebaseAnalytics @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : AppAnalytics {

    override fun logEvent(event: AppEvent) {
        firebaseAnalytics.logEvent(event.name,
            Bundle().apply {
                for (property in event.properties)
                    this.putString(property.key, property.value)
            }
        )
    }
}