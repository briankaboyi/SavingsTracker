package com.example.savingstrackerapp.Screens

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.savingstrackerapp.Goal
import com.example.savingstrackerapp.data.db.entities.GoalSavingsItem
import com.example.savingstrackerapp.data.repositories.GoalSavingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoalsViewModel(private val repository: GoalSavingsRepository) : ViewModel() {
    private val _goalList = mutableStateListOf<Goal>()
    val dataList: List<Goal> get() = _goalList

    fun addGoal(goal: Goal) {
        _goalList.add(goal)
    }

    fun upsert(item : GoalSavingsItem) =CoroutineScope(Dispatchers.Main).launch{repository.upsert(item)}
    fun delete(item : GoalSavingsItem) =CoroutineScope(Dispatchers.Main).launch{repository.delete(item)}
    fun getAllGoalSavingsItems() = repository.getAllGoalSavingsItems()

}