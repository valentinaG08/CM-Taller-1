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
import com.example.taller1.BuildConfig
import com.example.taller1.DestinationsActivity
import com.example.taller1.DetailsActivity
import com.example.taller1.FavoritesStore
import com.example.taller1.R
import com.example.taller1.utils.APIUtil
import com.google.gson.Gson

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

            if (FavoritesStore.getFavorites().size == 0) Toast.makeText(this, "No tienes ningún favorito", Toast.LENGTH_LONG).show()
            else {
                val intent = Intent(this, DestinationsActivity::class.java)
                intent.putExtra("category", "favorites")
                startActivity(intent)
            }
        }

        btnRecommendations.setOnClickListener {
            val favorites = FavoritesStore.getMostFrequentCategory();
            favorites.shuffle();

            if (favorites.size == 0) {
                Toast.makeText(this, "No tienes ningún favorito", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra("destination", Gson().toJson(favorites[0]))
                startActivity(intent)
            }
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