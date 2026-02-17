package com.example.savingstrackerapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.savingstrackerapp.R
import com.example.savingstrackerapp.ui.theme.BrightGreenColor

@Composable
fun TransactionItem(tx: com.example.savingstrackerapp.data.db.entities.TransactionEntity) {
    val isDeposit = tx.type.equals("DEPOSIT", ignoreCase = true)

    val iconRes = if (isDeposit) R.drawable.ic_deposit else R.drawable.ic_withdrawal
    val iconBg = if (isDeposit) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
    val amountColor = if (isDeposit) Color.Black else Color(0xFFD32F2F)
    val amountPrefix = if (isDeposit) "KES " else "KES "

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = tx.type,
                tint = if (isDeposit) BrightGreenColor else Color(0xFFD32F2F),
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            CustomText(
                text = tx.type.lowercase().replaceFirstChar { it.uppercase() },
                fontSize = 16,
                fontColor = Color.Black
            )
            CustomText(
                text = tx.method,
                fontSize = 13,
                fontColor = Color.Gray
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            CustomText(
                text = "$amountPrefix${"%.2f".format(tx.amount)}",
                fontSize = 16,
                fontColor = amountColor
            )
            CustomText(
                text = tx.date,
                fontSize = 13,
                fontColor = Color.Gray
            )
        }
    }
}