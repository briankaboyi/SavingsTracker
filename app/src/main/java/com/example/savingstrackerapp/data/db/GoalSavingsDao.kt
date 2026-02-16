package com.example.savingstrackerapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.savingstrackerapp.data.db.entities.GoalSavingsItem
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalSavingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: GoalSavingsItem)

    @Delete
    suspend fun delete(item: GoalSavingsItem)

    @Query("SELECT * FROM goal_savings")
    fun getAllGoalSavingsItems(): Flow<List<GoalSavingsItem>>

    @Query("SELECT * FROM goal_savings WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): GoalSavingsItem?
}