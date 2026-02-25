package com.example.savingstrackerapp.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.savingstrackerapp.ui.theme.BrightGreenColor
import com.example.savingstrackerapp.R
import com.example.savingstrackerapp.ui.theme.TextBlack

@Composable
fun SuccessDialog(
    title: String,
    subtitle: String,
    message: String? = null,
    buttonText: String = "Go to My Goals",
    onDismiss: () -> Unit = {},
    onButtonClick: () -> Unit = {}
) {
    var animStarted by remember { mutableStateOf(false) }
    val iconScale by animateFloatAsState(
        targetValue = if (animStarted) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "iconScale"
    )
    LaunchedEffect(Unit) { animStarted = true }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(TextBlack.copy(alpha = 0.45f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(R.drawable.success3d),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(108.dp),
                    contentDescription = "Success image",

                )

                Spacer(modifier = Modifier.height(20.dp))

                CustomText(
                    text = title,
                    fontSize = 18,
                    fontWeight = FontWeight.Bold,
                    fontColor = BrightGreenColor
                )

                Spacer(modifier = Modifier.height(4.dp))

                CustomText(
                    text = subtitle,
                    fontSize = 13,
                    fontWeight = FontWeight.Normal,
                    fontColor = Color.Gray
                )

                if (!message.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    CustomText(
                        text = message,
                        fontSize = 12,
                        fontWeight = FontWeight.Normal,
                        fontColor = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                CustomButton(
                    text = buttonText,
                    onClick = {
                        onButtonClick()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessDialogPreview() {
    SuccessDialog(
        title         = "${"%.2f".format(100)} KES",
        subtitle      = "Withdraw Successful",
        onDismiss     = {  },
        onButtonClick = { }
    )
}