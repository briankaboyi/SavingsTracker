package com.example.savingstrackerapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.savingstrackerapp.ui.screens.GoalsViewModel
import com.example.savingstrackerapp.data.db.GoalSavingsDatabase
import com.example.savingstrackerapp.data.repositories.GoalSavingsRepository
import com.example.savingstrackerapp.ui.navigation.AppNavHost
import com.example.savingstrackerapp.ui.theme.SavingsTrackerAppTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {

    private lateinit var goalSavingsDatabase: GoalSavingsDatabase
    private lateinit var goalsRepository: GoalSavingsRepository

    private lateinit var goalsViewModel: GoalsViewModel

    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goalSavingsDatabase = GoalSavingsDatabase.invoke(applicationContext)
        goalsRepository = GoalSavingsRepository(goalSavingsDatabase)

        // create ViewModel using factory now that repository is initialized
        val factory = GoalsViewModelFactory(goalsRepository)
        goalsViewModel = ViewModelProvider(this, factory).get(GoalsViewModel::class.java)

        enableEdgeToEdge()
        setContent {
            SavingsTrackerAppTheme {
                val navController = rememberNavController()

                AppNavHost(navController = navController, goalsViewModel = goalsViewModel)
            }
        }
    }
}

class GoalsViewModelFactory(private val repository: GoalSavingsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoalsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GoalsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
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
        val navController = rememberNavController()

//        HomeScreen(navController = navController)
    }
}
