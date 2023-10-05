package com.dam.riotapitest

import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Api().initApi()
        spinnerCreation()
        ui()
    }


    var selectedItem : Int = 0

    fun ui (){
        button2.setOnClickListener {
            Api().getMasteryPoints(editText.text.toString(), editText2.text.toString(), this, selectedItem) { masteryPoints ->
                textView3.text = "Puntos: "+masteryPoints.toString()
            }
        }
    }

    fun spinnerCreation(){
        val spinner = findViewById<Spinner>(R.id.serverSelector)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_items,
            android.R.layout.simple_spinner_item)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                selectedItem = parentView.selectedItemPosition
                Toast.makeText(
                    this@MainActivity,
                    "Selected Item: $selectedItem",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d("Error", "a")
            }

        }
    }
}

