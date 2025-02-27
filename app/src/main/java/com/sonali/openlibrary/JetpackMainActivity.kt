package com.sonali.openlibrary

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sonali.openlibrary.ui.theme.MyJetpackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyJetpackTheme {
                createBizCard()
            }
    }


}
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Preview
    @Composable
    private fun createBizCard() {
        Scaffold(
            topBar = {
            TopAppBar(backgroundColor = Color.Magenta) {
            Text(text = "This is top bar", color = Color.White, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
            }
        }){
            Surface(modifier = Modifier
                .padding(3.dp)
                .fillMaxWidth()
                .height(150.dp)
                .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
                color = Color.LightGray){
                Column(modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Hello Android", color = Color.Black, textAlign = TextAlign.Center, style = TextStyle(fontWeight = FontWeight.Bold))

                }

            }
        }

        
    }
    }

@Composable
private fun ShowAge(age: Int =34) {
    Text(text = age.toString())
}

//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyJetpackTheme {
        ShowAge(50)
    }
}