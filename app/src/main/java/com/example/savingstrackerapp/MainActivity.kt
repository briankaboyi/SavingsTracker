package com.example.savingstrackerapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.savingstrackerapp.Screens.CreateGoal
import com.example.savingstrackerapp.ui.theme.SavingsTrackerAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SavingsTrackerAppTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
                //pass createc
                val goalsViewModel = GoalsViewModel()
                CreateGoal(goalsViewModel )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SavingsTrackerAppTheme {
//        Greeting("Android")
//        SavingsCard()
//        SavingsList()
        HomePage()
    }
}

@Composable
fun SavingsCard(){
    Box(modifier = Modifier.fillMaxWidth().padding(16.dp).height(200.dp).background(color = Color.Green)){
//        Image(painter = painterResource(id = R.drawable.vault), contentDescription = "Savings Card", modifier = Modifier.fillMaxWidth(),)
        Column{
            CustomText(fontSize = 24, fontWeight = 700, text = "Goals Savings")
            CustomText(fontSize = 24, fontWeight = 700, text = "Turn Your Goals into Savings")
        }

    }

}
@Composable
fun CustomText(fontSize: Int, fontWeight: Int, text: String,fontColor:Color = Color.White){
    Text(
        text = text,
        modifier = Modifier.padding(16.dp),
        fontSize= fontSize.sp,
        color= fontColor
    )
}
@Composable
fun SavingsList(){
    LazyRow{
        items(1){
            Image(painter = painterResource(id = R.drawable.ad), contentDescription = "Savings Card", modifier = Modifier.fillMaxWidth(),)
            Image(painter = painterResource(id = R.drawable.ad__2_), contentDescription = "Savings Card", modifier = Modifier.fillMaxWidth(),)
        }

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(){
    Scaffold(topBar = {
        Box(modifier = Modifier.fillMaxWidth().height(56.dp).background(color = Color.Blue)){
            Image(painter = painterResource(id = R.drawable.user), contentDescription = "App Icon", modifier = Modifier.padding(8.dp))
            Text(text = "Savings Tracker", modifier = Modifier.align(Alignment.Center))
        }
    }) {  innerPadding ->

        Column{
            Spacer(modifier = Modifier.height(36.dp))
            CustomText(fontSize = 24, fontWeight = 700, text = "Start Saving Towards Your Goals", fontColor = Color.Black)
            SavingsCard()
            SavingsList()
        }

    }

}
