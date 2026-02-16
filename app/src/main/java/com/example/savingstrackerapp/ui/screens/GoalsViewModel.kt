package com.example.savingstrackerapp.ui.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savingstrackerapp.data.db.entities.GoalSavingsItem
import com.example.savingstrackerapp.data.repositories.GoalSavingsRepository
import kotlinx.coroutines.launch
import java.util.Date

class GoalsViewModel(private val repository: GoalSavingsRepository) : ViewModel() {
    private val _goalList = mutableStateListOf<GoalSavingsItem>()
    val dataList: List<GoalSavingsItem> get() = _goalList

    fun getAllGoalSavingsItems() = repository.getAllGoalSavingsItems()

    fun addGoal(goal: GoalSavingsItem) {
        _goalList.add(goal)
    }

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

    fun upsert(item : GoalSavingsItem) = viewModelScope.launch{repository.upsert(item)}
    fun delete(item : GoalSavingsItem) = viewModelScope.launch{repository.delete(item)}

    fun deposit(goalId: Int, amount: Double) {
        viewModelScope.launch {
            val item = repository.getById(goalId)
            if (item != null) {
                val updated = item.copy(currentAmount = item.currentAmount + amount)
                repository.upsert(updated)
            }
        }
    }

    fun withdraw(goalId: Int, amount: Double) {
        viewModelScope.launch {
            val item = repository.getById(goalId)
            if (item != null) {
                val newAmount = (item.currentAmount - amount).coerceAtLeast(0.0)
                val updated = item.copy(currentAmount = newAmount)
                repository.upsert(updated)
            }
        }
    }

}