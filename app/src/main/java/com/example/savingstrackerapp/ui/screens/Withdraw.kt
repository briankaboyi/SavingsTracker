package com.example.savingstrackerapp.ui.screens



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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.savingstrackerapp.R
import com.example.savingstrackerapp.ui.components.CustomButton
import com.example.savingstrackerapp.ui.components.CustomText
import com.example.savingstrackerapp.ui.components.CustomTopAppBar
import com.example.savingstrackerapp.ui.components.SuccessDialog
import com.example.savingstrackerapp.ui.theme.BrightGreenColor
import com.example.savingstrackerapp.ui.theme.FieldBorderColor
import com.example.savingstrackerapp.ui.theme.LabelColor
import com.example.savingstrackerapp.ui.viewmodels.GoalsViewModel

private enum class WithdrawMethod { COOP_ACCOUNT, M_PESA }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Withdraw(
//    goalsViewModel: GoalsViewModel
    navController: NavHostController,
    goalsViewModel: GoalsViewModel,
    goalId: Int
) {

    val goals = goalsViewModel.getAllGoalSavingsItems().collectAsState(initial = emptyList()).value

    var selectedGoalId by remember { mutableStateOf(0) }
    LaunchedEffect(goals) {
        if (goals.isNotEmpty()) {
            selectedGoalId = if (goalId != 0) goalId else goals.first().id
        }
    }

    var goalsExpanded   by remember { mutableStateOf(false) }

    val selectedGoalItem = goals.find { it.id == selectedGoalId }
    val selectedGoalName = selectedGoalItem?.name ?: ""
    val availableBalance = selectedGoalItem?.currentAmount ?: 0.0

    var withdrawMethod  by remember { mutableStateOf(WithdrawMethod.M_PESA) }
    var phoneNumber     by remember { mutableStateOf("") }
    var selectedAccountName by remember { mutableStateOf("") }
    var withdrawAmount  by remember { mutableStateOf("") }

    var showSuccessDialog by remember { mutableStateOf(false) }
    var successAmount by remember { mutableStateOf(0.0) }


    Scaffold(
        topBar = {
            CustomTopAppBar(
                navigationIcon = painterResource(id = R.drawable.arrow_back),
                titleContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CustomText(
                            text = "Withdraw",
                            fontWeight = FontWeight.Normal,
                            fontSize = 14
                        )
                    }
                },
                onNavigationIconClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->

        Box {
            if (showSuccessDialog) {
                SuccessDialog(
                    title         = "${"%.2f".format(successAmount)} KES",
                    subtitle      = "Withdraw Successful",
                    onDismiss     = { showSuccessDialog = false },
                    onButtonClick = { navController.popBackStack() }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 80.dp)
            ) {

                Spacer(modifier = Modifier.height(24.dp))


                CustomText(
                    text = stringResource(R.string.goal_name),
                    fontSize = 12,
                    fontColor = LabelColor
                )
                Spacer(modifier = Modifier.height(7.dp))

                ExposedDropdownMenuBox(
                    expanded = goalsExpanded,
                    onExpandedChange = { goalsExpanded = !goalsExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedGoalName,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(goalsExpanded)
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrightGreenColor,
                            unfocusedBorderColor = FieldBorderColor
                        ),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = goalsExpanded,
                        onDismissRequest = { goalsExpanded = false }
                    ) {
                        goals.forEach { goal ->
                            DropdownMenuItem(
                                text = { Text(goal.name) },
                                onClick = {
                                    selectedGoalId = goal.id
                                    goalsExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row {
                    CustomText(
                        text = "Available Balance:",
                        fontSize = 12,
                        fontColor = LabelColor
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    CustomText(
                        text = "${"%.2f".format(availableBalance)} KES",
                        fontSize = 12,
                        fontColor = BrightGreenColor
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))


                CustomText(
                    text = "WIthdraw to:",
                    fontSize = 12,
                    fontColor = LabelColor
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = withdrawMethod == WithdrawMethod.COOP_ACCOUNT,
                        onClick = { withdrawMethod = WithdrawMethod.COOP_ACCOUNT },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = BrightGreenColor,
                            unselectedColor = FieldBorderColor
                        )
                    )
                    CustomText(
                        text = "Coop Account",
                        fontSize = 14,
                        fontColor = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.width(24.dp))


                    RadioButton(
                        selected = withdrawMethod == WithdrawMethod.M_PESA,
                        onClick = { withdrawMethod = WithdrawMethod.M_PESA },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = BrightGreenColor,
                            unselectedColor = FieldBorderColor
                        )
                    )
                    CustomText(
                        text = "M-PESA",
                        fontSize = 14,
                        fontColor = if (withdrawMethod == WithdrawMethod.M_PESA)
                            BrightGreenColor else Color.DarkGray,
                        fontWeight = if (withdrawMethod == WithdrawMethod.M_PESA)
                            FontWeight.SemiBold else FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                CustomText(
                    text = "Phone Number",
                    fontSize = 12,
                    fontColor = LabelColor
                )
                Spacer(modifier = Modifier.height(7.dp))
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            tint = FieldBorderColor,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrightGreenColor,
                        unfocusedBorderColor = FieldBorderColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))


                CustomText(
                    text = "Amount to Withdraw",
                    fontSize = 12,
                    fontColor = LabelColor
                )
                Spacer(modifier = Modifier.height(7.dp))
                OutlinedTextField(
                    value = withdrawAmount,
                    onValueChange = { withdrawAmount = it },
                    singleLine = true,
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
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.weight(1f))


                CustomButton(
                    text = "Withdraw",
                    onClick = {
                        val amount = withdrawAmount.trim().toDoubleOrNull()

                        if (phoneNumber.isBlank()) {
                            // TODO: show validation error (Toast / Snackbar)
                            return@CustomButton
                        }
                        if (amount == null || amount <= 0) {
                            // TODO: show invalid amount error
                            return@CustomButton
                        }
                        if (amount > availableBalance) {
                            // TODO: show insufficient funds error
                            return@CustomButton
                        }

                        if (selectedGoalId == 0) return@CustomButton
                        val method = if (withdrawMethod == WithdrawMethod.COOP_ACCOUNT) selectedAccountName.ifBlank { "Coop Account" } else phoneNumber.ifBlank { "M-PESA" }
                        goalsViewModel.withdraw(selectedGoalId, amount, method)
//                        navController.popBackStack()
                        successAmount = amount
                        showSuccessDialog = true
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
