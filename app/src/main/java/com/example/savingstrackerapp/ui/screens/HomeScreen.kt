package com.example.savingstrackerapp.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.savingstrackerapp.data.db.entities.GoalSavingsItem

import com.example.savingstrackerapp.R
import com.example.savingstrackerapp.Transaction
import com.example.savingstrackerapp.TransactionFilter
import com.example.savingstrackerapp.TransactionType
import com.example.savingstrackerapp.ui.components.CustomButton
import com.example.savingstrackerapp.ui.components.CustomText
import com.example.savingstrackerapp.ui.components.CustomTopAppBar
import com.example.savingstrackerapp.ui.navigation.Screen
import com.example.savingstrackerapp.ui.theme.BrightGreenColor
import com.example.savingstrackerapp.ui.theme.FieldBorderColor
import com.example.savingstrackerapp.ui.theme.LabelColor


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController,
    goalsViewModel: GoalsViewModel? = null,
    // keep optional params for previews
    transactions: List<Transaction> = emptyList(),
    onAddGoal: () -> Unit = {},
    onDeposit: (GoalSavingsItem) -> Unit = {},
    onWithdraw: (GoalSavingsItem) -> Unit = {}
){
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
            onNavigationIconClick = { /* Handle navigation icon click */ }
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
                onDeposit = { goal -> navController.navigate(com.example.savingstrackerapp.ui.navigation.Screen.Deposit.createRoute(goal.id)) },
                onWithdraw = { goal -> navController.navigate(Screen.Withdraw.route) }
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
    onWithdraw: (GoalSavingsItem) -> Unit
) {
    var selectedFilter by remember { mutableStateOf(TransactionFilter.ALL) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF5F5F5))
    ) {

        // ── "My Goals" header ─────────────────────────────────────────────────
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
                // TODO: wire up onAddGoal click
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
                // Header row
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

                val filtered = when (selectedFilter) {
                    TransactionFilter.ALL         -> transactions
                    TransactionFilter.DEPOSITS    -> transactions.filter { it.type == TransactionType.DEPOSIT }
                    TransactionFilter.WITHDRAWALS -> transactions.filter { it.type == TransactionType.WITHDRAWAL }
                    else -> {}
                }

//                filtered.forEachIndexed { index, tx ->
//                    TransactionRow(transaction = tx)
//                    if (index < filtered.lastIndex) {
//                        HorizontalDivider(color = Color(0xFFF0F0F0))
//                    }
//                }
            }
        }
    }
}

// ─── Goal card ────────────────────────────────────────────────────────────────

@Composable
private fun GoalCard(
    goal: GoalSavingsItem,
    onDeposit: () -> Unit,
    onWithdraw: () -> Unit
) {
    // compute progress from current vs target
    val progress = if (goal.targetAmount > 0) {
        (goal.currentAmount / goal.targetAmount).coerceIn(0.0, 1.0).toFloat()
    } else 0f
    val progressPercent = "%.2f %%".format(progress * 100)

    Box(
        modifier = Modifier
            .width(331.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF2E7D32))

    ) {
        Image(
            painter = painterResource(id = R.drawable.goal_bg),
            contentDescription = "Savings Card",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop

        )
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Goal name + options icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = goal.name,
                    fontSize = 12,
                    fontColor = Color.White,
                    fontWeight = FontWeight.Normal
                )
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Options",
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                )
            }

            Spacer(modifier = Modifier.height(7.dp))

            // Balance + eye icon
            Row(verticalAlignment = Alignment.CenterVertically) {
                CustomText(
                    text = "${"%.2f".format(goal.currentAmount)} ",
                    fontSize = 28,
                    fontWeight = FontWeight.Bold,
                    fontColor = Color.White
                )
                CustomText(
                    text = "KES",
                    fontSize = 16,
                    fontWeight = FontWeight.Normal,
                    fontColor = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.user), // swap for eye/visibility icon
                    contentDescription = "Toggle visibility",
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Progress bar + percentage
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = BrightGreenColor,
                    trackColor = Color.White.copy(alpha = 0.3f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                CustomText(
                    text = progressPercent,
                    fontSize = 11,
                    fontColor = Color.White
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Target amount row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = stringResource(R.string.target_amount_kes),
                    fontSize = 11,
                    fontColor = Color.White.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                CustomText(
                    text = "%,.2f".format(goal.targetAmount),
                    fontSize = 13,
                    fontWeight = FontWeight.Bold,
                    fontColor = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    painter = painterResource(id = R.drawable.user), // swap for edit/pencil icon
                    contentDescription = "Edit target",
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(14.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Deposit / Withdraw buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Deposit – filled green
                CustomButton(
                    text = stringResource(R.string.deposit),
                    onClick = onDeposit,
                    modifier = Modifier.weight(1f)
                )

                CustomButton(
                    text =  "Withdraw",
                    onClick = onWithdraw,
                    modifier = Modifier.weight(1f),
                    borderColor = Color.White.copy(alpha = 0.5f),
                    containerColor = Color.Transparent,


                )
            }
        }
    }
}

// ─── Filter chip ──────────────────────────────────────────────────────────────

@Composable
private fun FilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (selected) Color.Black else Color.Transparent)
            .border(
                width = 1.dp,
                color = if (selected) Color.Black else FieldBorderColor,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 16.dp, vertical = 6.dp)
        // TODO: add clickable { onClick() }
    ) {
        CustomText(
            text = label,
            fontSize = 13,
            fontColor = if (selected) Color.White else Color.DarkGray,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

// ─── Transaction row ──────────────────────────────────────────────────────────

@Composable
private fun TransactionRow(transaction: Transaction) {
    val isDeposit = transaction.type == TransactionType.DEPOSIT
    val amountColor = if (isDeposit) Color.Black else Color.Red
    val amountPrefix = if (isDeposit) "KES " else "KES "

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon badge
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(
                    if (isDeposit) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isDeposit)
                    Icons.Filled.KeyboardArrowUp
                else
                    Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                tint = if (isDeposit) BrightGreenColor else Color.Red,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Type + method
        Column(modifier = Modifier.weight(1f)) {
            CustomText(
                text = if (isDeposit)
                    stringResource(R.string.tx_deposit)
                else
                    stringResource(R.string.tx_withdrawal),
                fontSize = 14,
                fontWeight = FontWeight.Medium,
                fontColor = Color.Black
            )
            CustomText(
                text = transaction.method,
                fontSize = 12 ,
                fontColor = LabelColor
            )
        }

        // Amount + date
        Column(horizontalAlignment = Alignment.End) {
            CustomText(
                text = "$amountPrefix${"%.2f".format(transaction.amount)}",
                fontSize = 14 ,
                fontWeight = if (isDeposit) FontWeight.Normal else FontWeight.Bold,
                fontColor = amountColor
            )
            CustomText(
                text = transaction.date,
                fontSize = 11 ,
                fontColor = LabelColor
            )
        }
    }
}



@Composable
fun SavingsCard(onClick:()->Unit={}){
    Box(

        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(199.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color(0xff48ac00))
            .clickable { onClick() }
    ){
        Image(
            painter = painterResource(id = R.drawable.savings_card_bg),
            contentDescription = "Savings Card",
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            CustomText(fontSize = 18, fontWeight = FontWeight.Bold, text = "Goals Savings")
            Spacer(modifier = Modifier.height(12.dp))

            CustomText(fontSize = 14, fontWeight = FontWeight.Normal, text = "Turn Your Goals into\nSavings")
        }
        Image(
            painter = painterResource(id = R.drawable.vault),
            contentDescription = "value=t",
            modifier = Modifier.fillMaxWidth( .5f)
                .align(Alignment.BottomEnd)

            ,
            contentScale = ContentScale.Crop
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
                modifier = Modifier.fillMaxWidth()
                    .size(300.dp,93.dp),
                contentScale = ContentScale.Fit,

            )

        }

    }
}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun HomeScreenEmptyPreview() {
//    HomeScreen(navController = rememberNavController())
//}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenWithDataPreview() {
    val now = java.util.Date()
    GoalsHomeContent(
        modifier = Modifier.padding(0.dp),
        goals = listOf(
            GoalSavingsItem(name = "Dubai Trip", targetAmount = 10000.0, targetDate = now, category = "Travel", currentAmount = 2000.0).apply { id = 1 },
            GoalSavingsItem(name = "Family Fund", targetAmount = 5000.0, targetDate = now, category = "Family", currentAmount = 1500.0).apply { id = 2 },
            GoalSavingsItem(name = "Emergency", targetAmount = 8000.0, targetDate = now, category = "Other", currentAmount = 3000.0).apply { id = 3 }
        ),
        transactions = listOf(
            Transaction(TransactionType.DEPOSIT,    "MPESA 0712345678",   600.0, "24 Sep 2025"),
            Transaction(TransactionType.WITHDRAWAL, "MPESA 0712345678",   200.0, "12 Oct 2025"),
            Transaction(TransactionType.DEPOSIT,    "Ac 01234567890412",  500.0, "24 Sep 2025")
        ),
        onAddGoal = {},
        onDeposit = {},
        onWithdraw = {}
    )
}