package com.example.savingstrackerapp.data.repositories

import com.example.savingstrackerapp.data.db.GoalSavingsDatabase
import com.example.savingstrackerapp.data.db.entities.GoalSavingsItem
import kotlinx.coroutines.flow.Flow

class GoalSavingsRepository(
    private val db: GoalSavingsDatabase

) {
    suspend fun upsert(item: GoalSavingsItem) =
        db.getGoalSavingsDao().upsert(item)

    suspend fun delete(item: GoalSavingsItem) =
        db.getGoalSavingsDao().delete(item)

    fun getAllGoalSavingsItems(): Flow<List<GoalSavingsItem>> = db.getGoalSavingsDao().getAllGoalSavingsItems()

    suspend fun getById(id: Int): GoalSavingsItem? = db.getGoalSavingsDao().getById(id)

}