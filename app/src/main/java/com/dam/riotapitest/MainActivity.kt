package com.dam.riotapitest

import android.graphics.BitmapFactory
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import java.io.File


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
            Api().getMasteryLevel(editText.text.toString(), editText2.text.toString(), this, selectedItem) { masteryLevel ->
                masteryLevelText.text = "Nivel de Maestría: "+masteryLevel.toString()
            }

        val imageResource = when (editText2.text.toString()) {
            "Aatrox" -> R.drawable.aatrox
            "Ahri" -> R.drawable.ahri
            "Akali" -> R.drawable.akali
            "Akshan" -> R.drawable.akshan
            "Alistar" -> R.drawable.alistar
            "Amumu" -> R.drawable.amumu
            "Anivia" -> R.drawable.anivia
            "Annie" -> R.drawable.annie
            "Aphelios" -> R.drawable.aphelios
            "Ashe" -> R.drawable.ashe
            "AurelionSol" -> R.drawable.aurelionsol
            "Azir" -> R.drawable.azir
            "Bard" -> R.drawable.bard
            "BelVeth" -> R.drawable.belveth
            "Blitzcrank" -> R.drawable.blitzcrank
            "Brand" -> R.drawable.brand
            "Braum" -> R.drawable.braum
            "Briar" -> R.drawable.briar
            "Caitlyn" -> R.drawable.caitlyn
            "Camille" -> R.drawable.camille
            "Cassiopea" -> R.drawable.cassiopea
            "ChoGath" -> R.drawable.chogath
            "Corki" -> R.drawable.corki
            "Darius" -> R.drawable.darius
            "Diana" -> R.drawable.diana
            "Draven" -> R.drawable.draven
            "DrMundo" -> R.drawable.drmundo
            "Ekko" -> R.drawable.ekko
            "Elise" -> R.drawable.elise
            "Evelynn" -> R.drawable.evelynn
            "Ezreal" -> R.drawable.ezreal
            "Fiddlesticks" -> R.drawable.fiddlesticks
            "Fiora" -> R.drawable.fiora
            "Fizz" -> R.drawable.fizz
            "Galio" -> R.drawable.galio
            "Mordekaiser" -> R.drawable.mordekaiser
            "MissFortune" -> R.drawable.missfortune
            else -> R.drawable.none
        }
        val myImageView = findViewById<ImageView>(R.id.champPhoto)
        myImageView.setImageResource(imageResource)
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

    fun createBorder(){
        val textView = findViewById<TextView>(R.id.button2)
        textView.setBackgroundResource(R.drawable.border)
        val textView2 = findViewById<TextView>(R.id.editText)
        textView2.setBackgroundResource(R.drawable.border)
        val textView3 = findViewById<TextView>(R.id.editText2)
        textView3.setBackgroundResource(R.drawable.border)
    }

    fun spinnerBorder(){
        val spinner = findViewById<Spinner>(R.id.serverSelector)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_items,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.setBackgroundResource(R.drawable.spinner_border)
    }
}

