package com.example.savingstrackerapp.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.savingstrackerapp.ui.theme.ProductSans

@Composable
fun CustomText(modifier: Modifier = Modifier,fontSize: Int, fontWeight: FontWeight = FontWeight.Normal, text: String,fontColor:Color = Color.White){
    Text(
        text = text,
        fontWeight = fontWeight,
        fontSize= fontSize.sp,
        color= fontColor,
        fontFamily = ProductSans,
        modifier = modifier
    )
}