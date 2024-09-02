package com.example.sihtest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
//import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.StateFlow

@Composable
fun VideoToText(
    isCameraPermissionGranted: StateFlow<Boolean>,
    navController: NavHostController
) {
    val context = LocalContext.current as MainActivity

    Column{
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(color = Color.Black, shape = RoundedCornerShape(20.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Video to Text Conversion",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    .height(40.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
        }

        HorizontalDivider(thickness = 10.dp, color = Color.Transparent)

        Column( // Video Player
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(400.dp)
                .padding(10.dp)
                .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp))
                .border(3.dp, Color.Black, RoundedCornerShape(20.dp))
        ) {
            Text(
                text = "Record Video"
            )

        }

        HorizontalDivider(thickness = 25.dp, color = Color.Transparent)

        Column(//Text Field
            modifier = Modifier
//                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(220.dp)
                .padding(10.dp)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
//                .border(3.dp, Color.Black, RoundedCornerShape(20.dp))
        ) {
            var text by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .border(3.dp, Color.Black, RoundedCornerShape(20.dp)),
                value = text,
                onValueChange = { text = it },
                shape = RoundedCornerShape(20.dp),
                placeholder = { Text("Translated text will be displayed here.") },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                )
            )
        }

        val permissionGranted by isCameraPermissionGranted.collectAsState()


        Column(//camera button
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Conditional UI rendering based on camera permission state
            if (permissionGranted) {
                LaunchedEffect(Unit) {
                    navController.navigate("CameraScreen")
                }
            } else {
                ElevatedButton(
                    onClick = { context.requestCameraPermission() },
                    modifier = Modifier.size(width = 300.dp, height = 40.dp),
                    shape = RoundedCornerShape(20.dp),
//                colors = ButtonDefaults.elevatedButtonColors(
//                    containerColor = Color.Blue
//                )
                ) {
                    Text(text = "Open Camera", color = Color.White)
                }
            }
        }

        Column(//translate button
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedButton(
                onClick = {},
                modifier = Modifier.size(width = 300.dp, height = 40.dp),
                shape = RoundedCornerShape(20.dp),
//                colors = ButtonDefaults.elevatedButtonColors(
//                    containerColor = Color.Blue
//                )
            ) {
                Text(text = "Translate", color = Color.White)
            }
        }

    }
}




