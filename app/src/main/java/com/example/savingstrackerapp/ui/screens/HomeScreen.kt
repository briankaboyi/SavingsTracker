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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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

import com.example.savingstrackerapp.R
import com.example.savingstrackerapp.ui.components.CustomText
import com.example.savingstrackerapp.ui.components.CustomTopAppBar
import com.example.savingstrackerapp.ui.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController){
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
        EmptyContent(navController)


    }

}
@Composable
fun EmptyContent(navController: NavHostController){
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
fun GoalsHomeContent(){

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

@Preview(showBackground = true)
@Composable
fun HomePagePreview(){
//    HomeScreen()
}