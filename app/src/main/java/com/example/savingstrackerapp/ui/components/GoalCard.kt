package com.example.savingstrackerapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.savingstrackerapp.R
import com.example.savingstrackerapp.data.db.entities.GoalSavingsItem
import com.example.savingstrackerapp.ui.theme.BrightGreenColor

@Composable
fun GoalCard(
    goal: GoalSavingsItem,
    onDeposit: () -> Unit,
    onWithdraw: () -> Unit
) {

    val progress = if (goal.targetAmount > 0) {
        (goal.currentAmount / goal.targetAmount).coerceIn(0.0, 1.0).toFloat()
    } else 0f
    val progressPercent = "%.2f %%".format(progress * 100)

    var amountVisible by remember{mutableStateOf(false)}
    var amountVisibilityIcon by remember{mutableStateOf(R.drawable.visible)}
    var targetAmountVisible by remember{mutableStateOf(false)}
    var targetAmountVisibilityIcon by remember{mutableStateOf(R.drawable.visible)}

    Box(
        modifier = Modifier
            .width(343.dp)
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

            Row(verticalAlignment = Alignment.CenterVertically) {
                CustomText(
                    text = "${"%.2f".format(goal.currentAmount)} ",
                    fontSize = 28,
                    fontWeight = FontWeight.Bold,
                    fontColor = Color.White,
                    modifier = Modifier.blur(radius = if (amountVisible) 0.dp else 8.dp)
                )
                CustomText(
                    text = "KES",
                    fontSize = 16,
                    fontWeight = FontWeight.Normal,
                    fontColor = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))


                IconButton(onClick = {
                    amountVisible = !amountVisible
                    if(amountVisible){
                        amountVisibilityIcon = R.drawable.not_visible
                    }else{
                        amountVisibilityIcon = R.drawable.visible
                    }
                }) {
                    Icon(
                        painter = painterResource(id = amountVisibilityIcon),
                        contentDescription = "Toggle visibility",
                        tint = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

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
//                targetAmountVisible
//                targetAmountVisibilityIcon
                IconButton(onClick = {
                    targetAmountVisible = !targetAmountVisible
                    if(targetAmountVisible){
                        targetAmountVisibilityIcon = R.drawable.not_visible
                    }else{
                        targetAmountVisibilityIcon = R.drawable.visible
                    }
                }) {
                    Icon(
                        painter = painterResource(id = targetAmountVisibilityIcon),
                        contentDescription = "Edit target",
                        tint = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.size(14.dp)
                    )
                }

            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
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