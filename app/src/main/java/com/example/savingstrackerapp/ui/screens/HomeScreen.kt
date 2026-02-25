package com.example.savingstrackerapp.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.savingstrackerapp.data.db.entities.GoalSavingsItem

import com.example.savingstrackerapp.R
import com.example.savingstrackerapp.data.transactions.Transaction
import com.example.savingstrackerapp.data.transactions.TransactionFilter
import com.example.savingstrackerapp.ui.components.CustomText
import com.example.savingstrackerapp.ui.components.CustomTopAppBar
import com.example.savingstrackerapp.ui.components.GoalCard
import com.example.savingstrackerapp.ui.components.FilterChip
import com.example.savingstrackerapp.ui.components.TransactionItem
import com.example.savingstrackerapp.ui.navigation.Screen
import com.example.savingstrackerapp.ui.theme.BrightGreenColor
import com.example.savingstrackerapp.ui.theme.SavingsTrackerAppTheme
import com.example.savingstrackerapp.ui.viewmodels.GoalsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController,
    goalsViewModel: GoalsViewModel = hiltViewModel(),
    transactions: List<Transaction> = emptyList(),
    onAddGoal: () -> Unit = {},
    onDeposit: (GoalSavingsItem) -> Unit = {},
    onWithdraw: (GoalSavingsItem) -> Unit = {}
){
    Log.d("HomeScreen", "HomeScreen recomposed with ${goalsViewModel?.getAllGoalSavingsItems()?.collectAsState(initial = emptyList())?.value?.size ?: 0} goals and ${transactions.size} transactions")
    val goalsEntities = goalsViewModel?.let { vm ->
        vm.getAllGoalSavingsItems().collectAsState(initial = emptyList()).value
    } ?: emptyList()

    val goals: List<GoalSavingsItem> = goalsEntities

    Scaffold(topBar = {
        CustomTopAppBar(
            navigationIcon = painterResource(id = R.drawable.user),
            titleContent = {
                Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CustomText(
                text = stringResource(R.string.hello_there),
                fontWeight = FontWeight.W700,
                fontSize = 20
            )
            CustomText(
                text = stringResource(R.string.its_a_good_day_to_save),
                fontWeight = FontWeight.W700,
                fontSize = 12
            )
        }
            },
            onNavigationIconClick = { }
        )

    }) {  innerPadding ->
        if (goals.isEmpty()) {
            EmptyHomeContent(
                modifier = Modifier.padding(innerPadding),
                onAddGoal = { navController.navigate(Screen.CreateGoal.route) },
                navController = navController
            )
        } else {
            GoalsHomeContent(
                modifier = Modifier.padding(innerPadding),
                goals = goals,
                transactions = transactions,
                onAddGoal = { navController.navigate(Screen.CreateGoal.route) },
                onDeposit = { goal -> navController.navigate(Screen.Deposit.createRoute(goal.id)) },
                onWithdraw = { goal -> navController.navigate(Screen.Withdraw.createRoute(goal.id)) },
                navController = navController,
                goalsViewModel = goalsViewModel
            )
        }
    }

}
@Composable
fun EmptyHomeContent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onAddGoal: () -> Unit = {}
){
    Column(horizontalAlignment= Alignment.CenterHorizontally){
        Spacer(modifier = Modifier.height(120.dp))
        CustomText(fontSize = 18, fontWeight = FontWeight.Normal, text = "Start Saving Towards Your Goals", fontColor = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        SavingsCard(onClick = {
            Log.d("HomeScreen", "SavingsCard clicked, navigate to CreateGoal")
            navController.navigate(Screen.CreateGoal.route)
        })
        Spacer(modifier = Modifier.height(38.dp))
        SavingsList()
    }
}
@Composable
private fun GoalsHomeContent(
    modifier: Modifier = Modifier,
    goals: List<GoalSavingsItem>,
    transactions: List<Transaction>,
    onAddGoal: () -> Unit,
    onDeposit: (GoalSavingsItem) -> Unit,
    onWithdraw: (GoalSavingsItem) -> Unit,
    navController: NavHostController,
    goalsViewModel: GoalsViewModel?
) {
    var selectedFilter by remember { mutableStateOf(TransactionFilter.ALL) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF5F5F5))
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomText(
                text = stringResource(R.string.my_goals),
                fontSize = 16,
                fontWeight = FontWeight.Bold,
                fontColor = Color.Black
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .padding(4.dp)
                    .clickable {
                        navController.navigate(Screen.CreateGoal.route)
                    }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_a_goal),
                    tint = BrightGreenColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                CustomText(
                    text = stringResource(R.string.add_a_goal),
                    fontSize = 14,
                    fontColor = BrightGreenColor,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(goals.size) { index ->
                GoalCard(
                    goal = goals[index],
                    onDeposit = { onDeposit(goals[index]) },
                    onWithdraw = { onWithdraw(goals[index]) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            goals.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(if (index == 1) 8.dp else 6.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == 1) BrightGreenColor
                            else Color(0xFFCCCCCC)
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomText(
                        text = stringResource(R.string.transaction_history),
                        fontSize = 16,
                        fontWeight = FontWeight.Bold,
                        fontColor = Color.Black
                    )
                    CustomText(
                        text = stringResource(R.string.view_all),
                        fontSize = 12,
                        fontColor = BrightGreenColor
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TransactionFilter.entries.forEach { filter ->
                        FilterChip(
                            label = when (filter) {
                                TransactionFilter.ALL         -> stringResource(R.string.filter_all)
                                TransactionFilter.DEPOSITS    -> stringResource(R.string.filter_deposits)
                                TransactionFilter.WITHDRAWALS -> stringResource(R.string.filter_withdrawals)
                            },
                            selected = selectedFilter == filter,
                            onClick = { selectedFilter = filter }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                val txEntities = goalsViewModel?.getAllTransactions()?.collectAsState(initial = emptyList())?.value ?: emptyList()

                val filtered = when (selectedFilter) {
                    TransactionFilter.ALL -> txEntities
                    TransactionFilter.DEPOSITS -> txEntities.filter { it.type == "DEPOSIT" }
                    TransactionFilter.WITHDRAWALS -> txEntities.filter { it.type == "WITHDRAWAL" }
                    else -> txEntities
                }

                // Table header
//                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
//                    Text(text = "Type", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray, modifier = Modifier.weight(1f))
//                    Text(text = "Method", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray, modifier = Modifier.weight(2f))
//                    Text(text = "Amount", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray, modifier = Modifier.weight(1f))
//                    Text(text = "Date", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray, modifier = Modifier.weight(1f))
//                }

                // Transactions rows
//                Column {
//                    filtered.forEachIndexed { index, tx ->
//                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
//                            Text(text = tx.type, fontSize = 12.sp, modifier = Modifier.weight(1f))
//                            Text(text = tx.method, fontSize = 12.sp, modifier = Modifier.weight(2f))
//                            Text(text = "KES ${"%.2f".format(tx.amount)}", fontSize = 12.sp, modifier = Modifier.weight(1f))
//                            Text(text = tx.date, fontSize = 12.sp, modifier = Modifier.weight(1f))
//                        }
//                        if (index < filtered.lastIndex) {
//                            HorizontalDivider(color = Color(0xFFF0F0F0))
//                        }
//                    }
//                }
                Column {
                    filtered.forEachIndexed { index, tx ->
                        TransactionItem(tx = tx)
                        if (index < filtered.lastIndex) {
                            HorizontalDivider(color = Color(0xFFF0F0F0))
                        }
                    }
                }
            }
        }
     }
 }





//@Composable
//private fun GoalCard(
//    goal: GoalSavingsItem,
//    onDeposit: () -> Unit,
//    onWithdraw: () -> Unit
//) {
//    val progress = if (goal.targetAmount > 0) {
//        (goal.currentAmount / goal.targetAmount).coerceIn(0.0, 1.0).toFloat()
//    } else 0f
//
//    Box(
//        modifier = Modifier
//            .width(300.dp)
//            .clip(RoundedCornerShape(12.dp))
//            .background(BrightGreenColor)
//            .padding(12.dp)
//    ) {
//        Column {
//            CustomText(fontSize = 14, fontWeight = FontWeight.SemiBold, text = goal.name, fontColor = Color.White)
//            Spacer(modifier = Modifier.height(8.dp))
//            CustomText(fontSize = 20, fontWeight = FontWeight.Bold, text = "${"%.2f".format(goal.currentAmount)} KES", fontColor = Color.White)
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//                LinearProgressIndicator(progress = progress, modifier = Modifier.weight(1f))
//                CustomButton(text = "Deposit", onClick = onDeposit, modifier = Modifier.weight(1f))
//                CustomButton(text = "Withdraw", onClick = onWithdraw, modifier = Modifier.weight(1f), containerColor = Color.Transparent, borderColor = Color.White)
//            }
//        }
//    }
//}


@Composable
fun SavingsCard(onClick:()->Unit={}){
    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = BrightGreenColor)
            .clickable { onClick() }
    ){
        Image(
            painter = painterResource(id = R.drawable.savings_card_bg),
            contentDescription = "Savings Card Background",
            modifier =Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
        )
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            CustomText(fontSize = 18, fontWeight = FontWeight.Bold, text = "Goals Savings")
            Spacer(modifier = Modifier.height(12.dp))
            CustomText(fontSize = 14, fontWeight = FontWeight.Normal, text = "Turn Your Goals into\nSavings")
        }
        Image(
            painter = painterResource(id = R.drawable.vault),
            contentDescription = "Savings Card Background",
            modifier =Modifier.height(97.dp).align(Alignment.BottomEnd),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun SavingsList(){
    val images = listOf(R.drawable.ad, R.drawable.ad__2_)
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ){
        items(images.size){ index->
            Image(
                painter = painterResource(id = images[index]),
                contentDescription = "Savings Card",
                modifier = Modifier
                    .size(300.dp,93.dp),
                contentScale = ContentScale.Fit,
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview(){
    SavingsTrackerAppTheme {
        HomeScreen(navController = rememberNavController())
    }
}
