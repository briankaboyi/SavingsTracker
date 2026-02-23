package com.example.savingstrackerapp.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savingstrackerapp.data.db.entities.GoalSavingsItem
import com.example.savingstrackerapp.data.db.entities.TransactionEntity
import com.example.savingstrackerapp.data.repositories.GoalSavingsRepository
import com.example.savingstrackerapp.data.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GoalsViewModel(private val repository: GoalSavingsRepository, private val txRepository: TransactionRepository) : ViewModel() {
    private val _goalList = mutableStateListOf<GoalSavingsItem>()
    val dataList: List<GoalSavingsItem> get() = _goalList

    fun getAllGoalSavingsItems() = repository.getAllGoalSavingsItems()

//    fun addGoal(goal: GoalSavingsItem) {
//        _goalList.add(goal)
//    }

//    fun upsert(item : GoalSavingsItem) = viewModelScope.launch{repository.upsert(item)}
//    fun delete(item : GoalSavingsItem) = viewModelScope.launch{repository.delete(item)}
    fun createGoal(name: String, targetAmount: Double, targetDate: Date, category: String) {
        val item = GoalSavingsItem(
            name = name,
            targetAmount = targetAmount,
            targetDate = targetDate,
            category = category,
            currentAmount = 0.0
        )
        viewModelScope.launch {
            repository.upsert(item)
        }
    }



    fun deposit(goalId: Int, amount: Double, method: String = "Unknown") {
        viewModelScope.launch {
            val item = repository.getById(goalId)
            if (item != null) {
                val updated = item.copy(currentAmount = item.currentAmount + amount)
                updated.id = item.id
                repository.upsert(updated)

                val date = SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(Date())
                val tx =
                    TransactionEntity(goalId = item.id, type = "DEPOSIT", method = method, amount = amount, date = date)
                txRepository.insert(tx)
            }
        }
    }

    fun withdraw(goalId: Int, amount: Double, method: String = "Unknown") {
        viewModelScope.launch {
            val item = repository.getById(goalId)
            if (item != null) {
                val newAmount = (item.currentAmount - amount).coerceAtLeast(0.0)
                val updated = item.copy(currentAmount = newAmount)
                updated.id = item.id
                repository.upsert(updated)

                val date = SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(Date())
                val tx = TransactionEntity(
                    goalId = item.id,
                    type = "WITHDRAWAL",
                    method = method,
                    amount = amount,
                    date = date
                )
                txRepository.insert(tx)
            }
        }
    }


    fun getAllTransactions(): Flow<List<TransactionEntity>> = txRepository.getAll()
//    fun getTransactionsByType(type: String): Flow<List<TransactionEntity>> = txRepository.getByType(type)

}