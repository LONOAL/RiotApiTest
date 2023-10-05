package com.dam.riotapitest

import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dam.riotapitest.ui.theme.RiotApiTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Api().initApi()
        ui()

    }


    fun ui (){
        button2.setOnClickListener {
            Api().getMasteryPoints(editText.text.toString(), editText2.text.toString(), this) { masteryPoints ->
                textView3.text = textView3.text.toString()+masteryPoints.toString()
            }
        }
    }

}