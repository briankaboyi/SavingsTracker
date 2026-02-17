package com.example.savingstrackerapp.data.repositories

import com.example.savingstrackerapp.data.db.GoalSavingsDatabase
import com.example.savingstrackerapp.data.db.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val db: GoalSavingsDatabase) {
    suspend fun insert(tx: TransactionEntity) = db.getTransactionDao().insert(tx)
    fun getAll(): Flow<List<TransactionEntity>> = db.getTransactionDao().getAllTransactions()
    fun getByType(type: String): Flow<List<TransactionEntity>> = db.getTransactionDao().getTransactionsByType(type)
}
