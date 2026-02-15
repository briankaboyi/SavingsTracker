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
import com.example.savingstrackerapp.ui.screens.GoalsViewModel
import com.example.savingstrackerapp.data.db.GoalSavingsDatabase
import com.example.savingstrackerapp.data.repositories.GoalSavingsRepository
import com.example.savingstrackerapp.ui.screens.HomePage
import com.example.savingstrackerapp.ui.theme.SavingsTrackerAppTheme

class MainActivity : ComponentActivity() {
    private val goalSavingsDatabase = GoalSavingsDatabase(this)
    private val goalsRepository = GoalSavingsRepository(goalSavingsDatabase)
    private val goalsViewModel by viewModels<GoalsViewModel>()
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        HomePage()
    }
}




