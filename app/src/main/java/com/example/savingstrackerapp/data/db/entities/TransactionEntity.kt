package com.example.savingstrackerapp.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @ColumnInfo(name = "goal_id")
    val goalId: Int,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "method")
    val method: String,
    @ColumnInfo(name = "amount")
    val amount: Double,
    @ColumnInfo(name = "date")
    val date: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
