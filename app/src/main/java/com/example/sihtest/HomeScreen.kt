package com.example.sihtest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sihtest.ui.theme.dark_grey
import com.example.sihtest.ui.theme.dark_grey
import com.example.sihtest.ui.theme.light_grey
import com.example.sihtest.ui.theme.muted_grey
import com.example.sihtest.ui.theme.elevated1
import com.example.sihtest.ui.theme.border1
import com.example.sihtest.ui.theme.border2
import com.example.sihtest.ui.theme.black1
import com.example.sihtest.ui.theme.sooth_blue
import com.example.sihtest.ui.theme.purple_pink

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

@Composable
fun HomeScreen(
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
//            .fillMaxWidth()
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Indian Sign Language Translator",
            textAlign = TextAlign.Center,
            letterSpacing = 1.0.sp,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp),
            lineHeight = 33.sp,
            color = black1
        )
        Image(
            painter = painterResource(id = R.drawable.people),
            contentDescription = "A call icon for calling",
            modifier = Modifier
                .width(300.dp)
                .height(300.dp),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )

        HorizontalDivider(thickness = 20.dp, color = Color.Transparent)

        ElevatedButton(//video to text
            onClick = {
                navHostController.navigate("VideoToText")
            },
            modifier = Modifier.size(width = 300.dp, height = 40.dp),
            shape = RoundedCornerShape(20.dp),
//                colors = ButtonDefaults.elevatedButtonColors(
//                    containerColor = Color.black1
//                )
        ) {
            Text(text = "Video to Text", color = Color.White)
        }


        HorizontalDivider(thickness = 20.dp, color = Color.Transparent)

        ElevatedButton(//text to video
            onClick = {
                navHostController.navigate("TextToVideo")
            },
            modifier = Modifier.size(width = 300.dp, height = 40.dp),
            shape = RoundedCornerShape(20.dp),
//                colors = ButtonDefaults.elevatedButtonColors(
//                    containerColor = Color.Blue
//                )
        ) {
            Text(text = "Text to Video", color = Color.White)
        }
    }

}