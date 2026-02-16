package com.example.savingstrackerapp.ui.screens


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange

import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.interaction.MutableInteractionSource
import android.content.ContextWrapper
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.savingstrackerapp.R
import com.example.savingstrackerapp.ui.components.CustomButton
import com.example.savingstrackerapp.ui.components.CustomText
import com.example.savingstrackerapp.ui.components.CustomTopAppBar
import com.example.savingstrackerapp.ui.theme.FieldBorderColor
import com.example.savingstrackerapp.ui.theme.BrightGreenColor
import com.example.savingstrackerapp.ui.theme.LabelColor
import java.util.Calendar
import android.util.Log
import android.widget.Toast





//a form to create a new goal, with fields for the goal name, target amount, and target date, a category selector, and a save button. When the user fills out the form and clicks the save button, the new goal should be added to the list of goals on the home screen.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGoal(
//    goalsViewModel: GoalsViewMode
navController: NavHostController
) {

    var goalName by remember { mutableStateOf("")}
    var goalCategory by remember { mutableStateOf("")}
    var targetAmount by remember { mutableStateOf("")}
    var targetDate by remember { mutableStateOf("")}
    val categories = listOf("Travelling","Family","Other")
    var expanded by remember {mutableStateOf(false)}
    val context = LocalContext.current

    /*

     */

    Scaffold(
        topBar = {
            CustomTopAppBar(
                navigationIcon = painterResource(id = R.drawable.user),
                titleContent = {
                    Column(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CustomText(
                            text = stringResource(R.string.create_goal),
                            fontWeight = FontWeight.Normal,
                            fontSize = 14
                        )

                    }
                },
                onNavigationIconClick = {  }
            )

        }

    ){innerPadding->
        Box(

        ){
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 80.dp)
            ) {

                Spacer(modifier = Modifier.height(46.dp))

                CustomText(
                    text = stringResource(R.string.goal_name),
                    fontSize = 12,
                    fontColor = LabelColor
                )
                Spacer(modifier = Modifier.height(7.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = goalName,
                    onValueChange = { goalName = it},
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrightGreenColor,
                        unfocusedBorderColor = FieldBorderColor
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))

                CustomText(
                    text = stringResource(R.string.goal_category),
                    fontSize = 12,
                    fontColor = LabelColor
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
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrightGreenColor,
                            unfocusedBorderColor = FieldBorderColor
                        ),
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryEditable, true)
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

                CustomText(
                    text = stringResource(R.string.target_amount),
                    fontSize = 12,
                    fontColor = LabelColor
                )
                Spacer(modifier = Modifier.height(7.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = targetAmount,
                    onValueChange = { targetAmount = it},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    leadingIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 12.dp)
                        ) {
                            Text(
                                text = "KES",
                                fontSize = 14.sp,
                                color = Color.DarkGray,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            VerticalDivider(
                                modifier = Modifier.height(24.dp),
                                color = FieldBorderColor
                            )
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrightGreenColor,
                        unfocusedBorderColor = FieldBorderColor
                    ),
                )
                Spacer(modifier = Modifier.height(16.dp))

                CustomText(
                    text = stringResource(R.string.savings_target_date),
                    fontSize = 12,
                    fontColor = LabelColor
                )
                Spacer(modifier = Modifier.height(7.dp))

                OutlinedTextField(
                    value = targetDate,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("DD/MM/YYYY", color = Color.LightGray) },
                    trailingIcon = {
                        IconButton(onClick = {
                            Log.d("CreateGoal", "Date trailingIcon clicked; context=${context.javaClass}")
                            showDatePicker(context) { date ->
                                targetDate = date
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = stringResource(R.string.select_date),
                                tint = BrightGreenColor
                            )
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrightGreenColor,
                        unfocusedBorderColor = FieldBorderColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            Log.d("CreateGoal", "Date field clicked; context=${context.javaClass}")
                            showDatePicker(context) { date ->
                                targetDate = date
                            }
                        }
                 )
                Spacer(modifier = Modifier.weight(1f))

                CustomButton(
                    text = stringResource(R.string.create_a_goal),
                    onClick = {
                        // validate inputs before creating/saving goal
                        val amount = targetAmount.trim().toDoubleOrNull()
                        if (goalName.isBlank()) {
                            // TODO: show user-facing validation (Toast/Snackbar). For now, ignore.
                            return@CustomButton
                        }
                        if (amount == null) {
                            // invalid number entered; TODO: show error to user. For now, ignore save.
                            return@CustomButton
                        }
                        // If you have a ViewModel or repository available, call it here to save the goal.
                        // Example (uncomment when using a ViewModel passed into this composable):
                        // goalsViewModel.addGoal(Goal(amount = amount, name = goalName, targetDate = targetDate, category = goalCategory))
                     },
                     modifier = Modifier
                         .fillMaxWidth()
                 )


             }
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
    // Unwrap ContextWrapper to find an Activity instance (LocalContext.current may be a themed wrapper)
    var ctx: Context? = context
    var activity: Activity? = null
    while (ctx != null) {
        if (ctx is Activity) {
            activity = ctx
            break
        }
        if (ctx is ContextWrapper) ctx = ctx.baseContext else break
    }
    if (activity == null) {
        Log.w("CreateGoal", "showDatePicker: could not find Activity from context=${context?.javaClass}")
        Toast.makeText(context, "Unable to open date picker", Toast.LENGTH_SHORT).show()
        return
    }

    val calendar = Calendar.getInstance()

    try {
        DatePickerDialog(
            activity,
            { _, year, month, day ->
                val date = "$day/${month + 1}/$year"
                onDateSelected(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    } catch (e: Exception) {
        Log.e("CreateGoal", "Error showing DatePickerDialog", e)
        Toast.makeText(activity, "Unable to open date picker: ${e.message}", Toast.LENGTH_SHORT).show()
    }
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
//    CreateGoal()
}