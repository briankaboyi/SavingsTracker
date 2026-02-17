package com.example.savingstrackerapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CreateGoal : Screen("create_goal")
    object Withdraw : Screen("withdraw/{goalId}") {
        fun createRoute(goalId: Int) = "withdraw/$goalId"
    }
    object Deposit : Screen("deposit/{goalId}") {
        fun createRoute(goalId: Int) = "deposit/$goalId"
    }
    object GoalDetails : Screen("goal_details/{goalId}") {
        fun createRoute(goalId: String) = "goal_details/$goalId"
    }
}