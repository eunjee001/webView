package com.kkyoungs.webview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kkyoungs.webview.ui.theme.WebViewTheme


// 컴포즈에서는 웹뷰를 아직 지원하지 않는다.

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun HomeScreen(){
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "나만의 웹브라우져") },
            actions = {
                IconButton(onClick = {

                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = Color.White
                    )
                }

                IconButton(onClick = {

                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "forward",
                        tint = Color.White
                    )
                }


            })} )
    {

    }

}