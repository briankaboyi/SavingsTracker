package com.example.savingstrackerapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.savingstrackerapp.ui.theme.FieldBorderColor
import com.example.savingstrackerapp.ui.theme.TextBlack

@Composable
fun FilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (selected) TextBlack else Color.Transparent)
            .border(
                width = 1.dp,
                color = if (selected) TextBlack else FieldBorderColor,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        CustomText(
            fontSize = 13,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
            text = label,
            fontColor = if (selected) Color.White else Color.DarkGray
        )
    }
}