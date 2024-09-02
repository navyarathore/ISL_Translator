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
//import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextToVideo() {
    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(color = Color.Black, shape = RoundedCornerShape(20.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Text to Video Conversion",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    .height(40.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
        }

        HorizontalDivider(thickness = 10.dp, color = Color.Transparent)

        var text by remember { mutableStateOf("") }
        Column(//Text Field
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(200.dp)
                .padding(10.dp)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
//                .border(3.dp, Color.Black, RoundedCornerShape(20.dp))
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .border(3.dp, Color.Black, RoundedCornerShape(20.dp)),
                value = text,
                onValueChange = { text = it },
                shape = RoundedCornerShape(20.dp),
                placeholder = { Text("Enter the text you want to be translated.") },

                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                )
            )
        }

            HorizontalDivider(thickness = 35.dp, color = Color.Transparent)


        val videos = remember {
            mutableStateOf<Collection<String>?>(null)
        }
        Column(//video player
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(10.dp)
                .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp))
                .border(3.dp, Color.Black, RoundedCornerShape(20.dp))
        ) {
            ExoPlayerView(videos)
        }

        Column(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedButton(
                onClick = {
                    runBlocking {
                        requestApi(text)
                    }
                },
                modifier = Modifier.size(width = 300.dp, height = 40.dp),
                shape = RoundedCornerShape(20.dp),
//                colors = ButtonDefaults.elevatedButtonColors(
//                    containerColor = Color.Blue
//                )
            ) {
                Text(text = "Convert to Sign", color = Color.White)
            }
        }
    }
}

//@file:OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExoPlayerView(url: Collection<String>) {

    val context = LocalContext.current


    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }


    val mediaSource = remember {
        MediaItem.fromUri(url) //need to replace with actual url.
    }


    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
    }


    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }


    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }
}

data class Resp(
    val text: String,
    val urls: Map<String, String>
)

suspend fun requestApi(text: String): Collection<String> {
    val res = client.post("http://13.232.127.156:8000/translate") {
        contentType(ContentType.Application.Json)
        setBody(buildJsonObject {
            put("text", JsonPrimitive(text))
        })
    }

    return res.body<Resp>().urls.values
}