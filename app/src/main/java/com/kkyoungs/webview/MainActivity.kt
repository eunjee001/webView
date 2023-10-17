package com.kkyoungs.webview

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


// 컴포즈에서는 웹뷰를 아직 지원하지 않는다.

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel= viewModel<MainViewModel>()
            HomeScreen(viewModel)
        }
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel){
    // LocalFocusManager : 여러 컴포저블간 포커싱 이동에 유용하게 사용.
    val focusManager = LocalFocusManager.current

    // remember 은 재구성 과정 전체에서 상태를 유지하는데 도움은 되지만 구성 변경 전반에서는 상태가 유지되지 않는다 --> rememberSaveable 사용
    // Bundle에 저장할 수 있는 모든 값을 자동으로 저장한다.

    val (inputUrl, setUrl) = rememberSaveable{
        mutableStateOf("https://www.google.com")
    }

    //디폴트값 사용한 변수 생성
    val scaffoldState = rememberScaffoldState()

    // Scaffold 는 화면 상태를 기억한다.
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "나만의 웹브라우져") },
            actions = {
                IconButton(onClick = {
                    viewModel.undo()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = Color.White
                    )
                }

                IconButton(onClick = {
                    viewModel.redo()
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "forward",
                        tint = Color.White
                    )
                }


            })
    }, scaffoldState = scaffoldState, )
    {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize())
        {
            OutlinedTextField(value = inputUrl, onValueChange = setUrl, label = { Text(text = "https://")}, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search), keyboardActions = KeyboardActions(onSearch = {
                viewModel.url.value = inputUrl
                // focus 가 사라지면서 자동으로 키보드가 내려간다.
                focusManager.clearFocus()
            }) )
            Spacer(modifier = Modifier.height(16.dp))
            MyWebView(viewModel = viewModel, scaffoldState = scaffoldState)
        }
    }

}
@Composable
fun MyWebView(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
){
    val webView = rememberWebView()

    LaunchedEffect(Unit){
        viewModel.undoSharedFlow.collectLatest {
            if (webView.canGoBack()){
                webView.goBack()
            }else{
                scaffoldState.snackbarHostState.showSnackbar("더 이상 뒤로 갈 수 없음")
            }
        }
    }
    LaunchedEffect(Unit){
        viewModel.redoSharedFlow.collectLatest {
            if (webView.canGoForward()){
                webView.goForward()
            }else{

                scaffoldState.snackbarHostState.showSnackbar("더 이상 앞으로 갈 수 없음")
            }
        }

    }
    AndroidView(modifier = Modifier.fillMaxSize(), factory = {webView},
        update = {
            webView -> webView.loadUrl(viewModel.url.value)

    })
}
@Composable
fun rememberWebView():WebView{
    val context = LocalContext.current
    val webView = remember {
        WebView(context).apply {
            // 기본적으로 자바 스크립트는 WebView에서 사용 중지된다. 자바 스크립트는 WebView에 연결된 WebSettings를 통해 사용 설정.
            // getSetting() 로 WebSettings를 가져온 다음 setJavaScriptEnable()로 자바스크립트를 사용 설정 할 수 있다.

            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl("https://google.com") }
    }
    return webView
}