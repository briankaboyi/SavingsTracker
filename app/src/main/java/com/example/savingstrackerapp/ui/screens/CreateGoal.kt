package com.example.savingstrackerapp.ui.screens


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar





//a form to create a new goal, with fields for the goal name, target amount, and target date, a category selector, and a save button. When the user fills out the form and clicks the save button, the new goal should be added to the list of goals on the home screen.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGoal(
//    goalsViewModel: GoalsViewModel
) {

    var goalName by remember { mutableStateOf("")}
    var goalCategory by remember { mutableStateOf("")}
    var targetAmount by remember { mutableStateOf("")}
    var targetDate by remember { mutableStateOf("")}
    val categories = listOf("Travelling","Family","Other")
    var expanded by remember {mutableStateOf(false)}
    val context = LocalContext.current

    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Create a Goal") },
//                navigationIcon = {
//                    IconButton(onClick = {  }){
//
//                        Icon(
//                            imageVector = Icons.Filled.ArrowBack,
//                            contentDescription = "Back"
//                        )
//                    }
//
//                }
//            )
//        },

    ){innerPadding->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)
            .verticalScroll(rememberScrollState())) {
            // Goal Name Input
            Spacer(modifier = Modifier.height(46.dp))

            Text(
                text = "Goal Name",
                fontSize = 12.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(7.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().fillMaxHeight() ,
                value = goalName,
                onValueChange = { goalName = it},
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Goal Category",
                fontSize = 12.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(7.dp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {

                OutlinedTextField(
                    value = goalCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                goalCategory = category
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Target Amount",
                fontSize = 12.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(7.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().height(44.dp),
                value = targetAmount,
                onValueChange = { targetAmount = it},
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Target Amount",
                fontSize = 12.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(7.dp))
            OutlinedTextField(
                value = targetDate,
                onValueChange = {},
                readOnly = true,
                label = { Text("Date") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showDatePicker(context) { date ->
                            targetDate = date
                        }
                    }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
//                goalsViewModel.addGoal(Goal(
//                    amount = targetAmount.toDouble(), name = goalName,
//                    targetDate = "Dummy date",
//                    category = "Dummy category"
//                ))

            }) {
                Text(text = "Save")
            }

//            GoalsList(
////                goalsViewModel
//            )
            // Save Button
        }
    }




}
@Composable
fun GoalsList(viewModel: GoalsViewModel ) {

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(viewModel.dataList.size) { index ->
            Text(text = viewModel.dataList[index].name, fontSize = 20.sp, color = Color.Black)
//            Image(painter = painterResource(id = R.drawable.ad), contentDescription = "Savings Card", modifier = Modifier.fillMaxWidth(),)
//            Image(painter = painterResource(id = R.drawable.ad__2_), contentDescription = "Savings Card", modifier = Modifier.fillMaxWidth(),)
        }

    }
}
fun showDatePicker(
    context: Context,
    onDateSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()

    DatePickerDialog(
        context,
        { _, year, month, day ->
            val date = "$day/${month + 1}/$year"
            onDateSelected(date)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

@SuppressLint("ViewModelConstructorInComposable")
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CreateGoalPreview() {
//    val context = LocalContext.current
//    val goalSavingsDatabase = GoalSavingsDatabase(context)
//    val goalsRepository = GoalSavingsRepository(goalSavingsDatabase)
//    val goalsViewModel = GoalsViewModel(goalsRepository)
//    CreateGoal(goalsViewModel)
    CreateGoal()
}