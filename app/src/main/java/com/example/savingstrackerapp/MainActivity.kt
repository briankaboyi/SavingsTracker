package com.example.savingstrackerapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.savingstrackerapp.ui.screens.GoalsViewModel
import com.example.savingstrackerapp.data.db.GoalSavingsDatabase
import com.example.savingstrackerapp.data.repositories.GoalSavingsRepository
import com.example.savingstrackerapp.data.repositories.TransactionRepository
import com.example.savingstrackerapp.ui.navigation.AppNavHost
import com.example.savingstrackerapp.ui.theme.SavingsTrackerAppTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import androidx.compose.material3.Text
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private lateinit var goalSavingsDatabase: GoalSavingsDatabase
    private lateinit var goalsRepository: GoalSavingsRepository
    private lateinit var transactionRepository: TransactionRepository

    private var goalsViewModel: GoalsViewModel? = null

    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            SavingsTrackerAppTheme {
                var initialized by remember { mutableStateOf(false) }
                var initializationError by remember { mutableStateOf<String?>(null) }
                val activity = this@MainActivity

                LaunchedEffect(Unit) {
                    try {
                        withContext(Dispatchers.IO) {
                            goalSavingsDatabase = GoalSavingsDatabase.invoke(applicationContext)
                            goalsRepository = GoalSavingsRepository(goalSavingsDatabase)
                            transactionRepository = TransactionRepository(goalSavingsDatabase)
                        }

                        val factory = GoalsViewModelFactory(goalsRepository, transactionRepository)
                        goalsViewModel = ViewModelProvider(activity, factory).get(GoalsViewModel::class.java)

                        initialized = true
                    } catch (e: Exception) {
                        initializationError = e.message ?: e.toString()
                    }
                }

                if (!initialized) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            if (initializationError == null) {
                                CircularProgressIndicator()
                            } else {
                                Text(text = "Initialization error: $initializationError")
                            }
                        }
                    }
                } else {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController, goalsViewModel = goalsViewModel!!)
                }
            }
        }
    }
}

class GoalsViewModelFactory(private val repository: GoalSavingsRepository, private val txRepository: TransactionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoalsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GoalsViewModel(repository, txRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SavingsTrackerAppTheme {
    }
}
