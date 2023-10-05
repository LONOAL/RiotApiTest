package com.dam.riotapitest

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dam.riotapitest.ui.theme.RiotApiTestTheme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Api().initApi()
        Api().getMasteryPoints("LONOAL02", "Swain", this) { masteryPoints ->
            Log.d("Hola", "Puntos de Maestría: $masteryPoints")
        }

        setContent {
            RiotApiTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting(applicationContext)
                }
            }
        }
    }


}



@Composable
fun Greeting(context : Context) {
    var champName = remember {
        mutableStateOf("")
    }
    var points = remember {
        mutableStateOf(0)
    }

    Column(Modifier.padding(16.dp)){
        TextField(value = champName.value, onValueChange = { champName.value = it }, Modifier.padding(16.dp))
        Button(onClick = { Api().getMasteryPoints("LONOAL02",champName.value,context){ masteryPoints ->
            points.value=masteryPoints
        }
        }) {

        }
        Text(text = points.value.toString())
    }

}
