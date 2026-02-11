package com.example.savingstrackerapp.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.savingstrackerapp.Goal
import com.example.savingstrackerapp.Screens.GoalsViewModel
import com.example.savingstrackerapp.R
import com.example.savingstrackerapp.SavingsList


//a form to create a new goal, with fields for the goal name, target amount, and target date, a category selector, and a save button. When the user fills out the form and clicks the save button, the new goal should be added to the list of goals on the home screen.

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun CreateGoal(goalsViewModel: GoalsViewModel) {

    var goalName by remember { mutableStateOf("")}
    var targetAmount by remember { mutableStateOf("")}

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Goal Name Input
        Spacer(modifier = Modifier.height(46.dp))

        Text(
            text = "Goal Name",
            fontSize = 20.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().height(44.dp),
            value = goalName,
            onValueChange = { goalName = it},
        )
        Spacer(modifier = Modifier.height(46.dp))

        Text(
            text = "Target Amount",
            fontSize = 20.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().height(44.dp),
            value = targetAmount,
            onValueChange = { targetAmount = it},
        )
        Button(onClick = {
            goalsViewModel.addGoal(Goal(
                amount = targetAmount.toDouble(), name = goalName,
                targetDate = "Dummy date",
                category = "Dummy category"
            ))

        }) {
            Text(text = "Save")
        }

        GoalsList( goalsViewModel)
        // Save Button
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
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CreateGoalPreview() {
//    CreateGoal()
}