package com.example.savingstrackerapp.data.repositories

import com.example.savingstrackerapp.data.db.GoalSavingsDatabase
import com.example.savingstrackerapp.data.db.entities.GoalSavingsItem

class GoalSavingsRepository(
    private val db: GoalSavingsDatabase

) {
    suspend fun upsert(item: GoalSavingsItem) =
        db.getGoalSavingsDao().upsert(item)

    suspend fun delete(item: GoalSavingsItem) =
        db.getGoalSavingsDao().delete(item)

    fun getAllGoalSavingsItems() = db.getGoalSavingsDao().getAllGoalSavingsItems()

}