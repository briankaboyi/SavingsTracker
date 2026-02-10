package com.example.savingstrackerapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel


class GoalsViewModel : ViewModel() {
    private val _goalList = mutableStateListOf<Goal>()
    val dataList: List<Goal> get() = _goalList

    fun addGoal(goal: Goal) {
        _goalList.add(goal)
    }
}