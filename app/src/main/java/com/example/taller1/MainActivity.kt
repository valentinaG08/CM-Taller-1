package com.example.testapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.taller1.DestinationsActivity
import com.example.taller1.R


class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerCategory: Spinner = findViewById(R.id.spinnerCategory)
        val btnExplore: Button = findViewById(R.id.btnExplore)
        val btnFavorites: Button = findViewById(R.id.btnFavorites)
        val btnRecommendations: Button = findViewById(R.id.btnRecommendations)

        val categories = resources.getStringArray(R.array.categorias_viajes)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        btnExplore.setOnClickListener {
            val selectedCategory = spinnerCategory.selectedItem.toString()
            val intent = Intent(this, DestinationsActivity::class.java)
            intent.putExtra("category", selectedCategory)
            startActivity(intent)
        }

        btnFavorites.setOnClickListener {
            // Abre la actividad de favoritos
        }

        btnRecommendations.setOnClickListener {
            // Abre la actividad de recomendaciones
        }

        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCategory = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Acciones adicionales si no se selecciona nada
            }
        }
    }
}