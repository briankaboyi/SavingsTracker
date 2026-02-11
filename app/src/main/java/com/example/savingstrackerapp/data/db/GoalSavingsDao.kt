package com.example.savingstrackerapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.savingstrackerapp.data.db.entities.GoalSavingsItem

@Dao
interface GoalSavingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: GoalSavingsItem)

    @Delete
    suspend fun delete(item: GoalSavingsItem)

    @Query("SELECT * FROM goal_savings")
    fun getAllGoalSavingsItems(): LiveData<List<GoalSavingsItem>>
}