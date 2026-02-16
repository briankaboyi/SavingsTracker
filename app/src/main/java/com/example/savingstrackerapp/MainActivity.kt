package com.example.savingstrackerapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.savingstrackerapp.ui.screens.GoalsViewModel
import com.example.savingstrackerapp.data.db.GoalSavingsDatabase
import com.example.savingstrackerapp.data.repositories.GoalSavingsRepository
import com.example.savingstrackerapp.ui.navigation.AppNavHost
import com.example.savingstrackerapp.ui.screens.HomeScreen
import com.example.savingstrackerapp.ui.theme.SavingsTrackerAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var goalSavingsDatabase: GoalSavingsDatabase
    private lateinit var goalsRepository: GoalSavingsRepository

    private val goalsViewModel by viewModels<GoalsViewModel>()
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goalSavingsDatabase = GoalSavingsDatabase.invoke(applicationContext)
        goalsRepository = GoalSavingsRepository(goalSavingsDatabase)

        enableEdgeToEdge()
        setContent {
            SavingsTrackerAppTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
                //pass createc

//                CreateGoal(goalsViewModel )

                val navController = rememberNavController()

                AppNavHost(navController = navController)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SavingsTrackerAppTheme {
//        Greeting("Android")
//        SavingsCard()
//        SavingsList()
        val navController = rememberNavController()

        HomeScreen(navController)
    }
}
