package com.example.savingstrackerapp.di

import android.content.Context
import com.example.savingstrackerapp.data.db.GoalSavingsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provieDatabase(
       @ApplicationContext context: Context
    ): GoalSavingsDatabase {
        return GoalSavingsDatabase.invoke(context)
    }
}