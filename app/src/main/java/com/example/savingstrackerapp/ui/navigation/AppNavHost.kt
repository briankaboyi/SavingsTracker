package com.example.savingstrackerapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.savingstrackerapp.ui.screens.CreateGoal
import com.example.savingstrackerapp.ui.screens.HomeScreen
import com.example.savingstrackerapp.ui.screens.Withdraw

@Composable
fun AppNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(Screen.CreateGoal.route) {
            CreateGoal(navController)
        }
        composable(Screen.Withdraw.route) {
            Withdraw(navController)
        }


    }
}
