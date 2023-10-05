package com.dam.riotapitest

import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import androidx.activity.ComponentActivity


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