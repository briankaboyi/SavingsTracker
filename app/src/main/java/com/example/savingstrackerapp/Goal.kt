package com.example.savingstrackerapp

data class Goal(val amount: Double, val name: String,var targetDate: String,var category: String) {
    var currentAmount: Double = 0.0

    fun addSavings(amount: Double) {
        currentAmount += amount
    }

    fun isGoalReached(): Boolean {
        return currentAmount >= this.amount
    }

    fun getProgress(): Double {
        return (currentAmount / amount) * 100
    }

    fun getRemainingAmount(): Double {
        return amount - currentAmount
    }

}
