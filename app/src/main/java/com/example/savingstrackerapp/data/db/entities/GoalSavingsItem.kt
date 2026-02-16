package com.example.savingstrackerapp.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "goal_savings")
data class GoalSavingsItem(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "target_amount")
    val targetAmount: Double,
    @ColumnInfo(name = "target_date")
    var targetDate: Date,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "current_amount")
    val currentAmount: Double = 0.0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}