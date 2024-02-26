package com.example.taller1

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream

class DestinationsActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinations)

        listView = findViewById(R.id.listViewDestinations)
        category = intent.getStringExtra("category") ?: "Todos"

        title = "Destinos - $category"

        val destinationsList = loadDestinationsFromJson()
        val filteredDestinations = filterDestinationsByCategory(destinationsList, category)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, filteredDestinations.map { it.nombre })
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedDestination = filteredDestinations[position]
            openDetailsActivity(selectedDestination)
        }
    }

    private fun openDetailsActivity(destination: Destination) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("destination", Gson().toJson(destination))
        startActivity(intent)
    }

    private fun loadDestinationsFromJson(): List<Destination> {
        val json: String? = loadJSONFromAsset("destinos.json")
        val gson = Gson()

        // adapta para manejar el objeto JSON que contiene la lista de destinos
        val destinationWrapper = gson.fromJson(json, DestinationWrapper::class.java)

        // Retorna la lista de destinos
        return destinationWrapper?.destinos ?: emptyList()
    }

    private fun loadJSONFromAsset(filename: String): String? {
        var json: String? = null
        try {
            val inputStream: InputStream = assets.open(filename)
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }

    private fun filterDestinationsByCategory(destinations: List<Destination>, category: String): List<Destination> {
        return if (category == "Todos") {
            destinations
        } else {
            destinations.filter { it.categoria == category }
        }
    }

    data class DestinationWrapper(val destinos: List<Destination>?)
}
