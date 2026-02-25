package com.example.savingstrackerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.savingstrackerapp.ui.screens.CreateGoal
import com.example.savingstrackerapp.ui.screens.HomeScreen
import com.example.savingstrackerapp.ui.screens.Withdraw
import com.example.savingstrackerapp.ui.screens.Deposit
import com.example.savingstrackerapp.ui.viewmodels.GoalsViewModel

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.CreateGoal.route) {
            CreateGoal(navController = navController)
        }

        composable(
            route = Screen.Deposit.route,
            arguments = listOf(navArgument("goalId") { type = NavType.IntType })
        ) { backStackEntry ->
            val goalId = backStackEntry.arguments?.getInt("goalId") ?: 0
            Deposit(navController = navController,  goalId = goalId)
        }

        composable(
            route = Screen.Withdraw.route,
            arguments = listOf(navArgument("goalId") { type = NavType.IntType })
        ) { backStackEntry ->
            val goalId = backStackEntry.arguments?.getInt("goalId") ?: 0
            Withdraw(navController = navController, goalId = goalId)
        }

    }
}
