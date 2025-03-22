package com.example.task.di

import android.content.Context
import androidx.room.Room
import com.example.task.analytics.AppAnalytics
import com.example.task.analytics.FirebaseAnalytics
import com.example.task.data.TaskRepository
import com.example.task.data.TaskRepositoryImpl
import com.example.task.data.database.AppDatabase
import com.example.task.data.network.ApiService
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        val databaseName = "task-app"
        return Room.databaseBuilder(context, AppDatabase::class.java, databaseName).build()
    }

    @Provides
    fun provideTaskRepository(apiService: ApiService, appDatabase: AppDatabase): TaskRepository {
        return TaskRepositoryImpl(apiService, appDatabase)
    }

    @Singleton
    @Provides
    fun provideAppAnalytics(): AppAnalytics {
        return FirebaseAnalytics(Firebase.analytics)
    }
}