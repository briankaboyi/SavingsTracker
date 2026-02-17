package com.example.savingstrackerapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.savingstrackerapp.R
import com.example.savingstrackerapp.ui.theme.TopAppBarColor

@Composable
fun CustomTopAppBar(
    navigationIcon: Painter,
    titleContent:@Composable () -> Unit,
    onNavigationIconClick:()->Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(TopAppBarColor)
            .padding(horizontal = 16.dp)
    ) {

        titleContent()

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(48.dp)
                .background(color = TopAppBarColor)
                .padding(8.dp)
                .clickable { onNavigationIconClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = navigationIcon,//painterResource(id = R.drawable.user)
                contentDescription = "User",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(48.dp)
            )
        }

    }
}