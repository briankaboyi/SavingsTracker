package com.example.savingstrackerapp.di

import com.example.savingstrackerapp.data.db.GoalSavingsDatabase
import com.example.savingstrackerapp.data.repositories.GoalSavingsRepository
import com.example.savingstrackerapp.data.repositories.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideGoalSavingsRepository(
        db: GoalSavingsDatabase
    ): GoalSavingsRepository {
        return GoalSavingsRepository(db)
    }


    @Provides
    @Singleton
    fun provideTransactionRepository(
        db: GoalSavingsDatabase
    ): TransactionRepository {
        return TransactionRepository(db)
    }
}