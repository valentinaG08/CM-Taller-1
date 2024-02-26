package com.example.taller1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Obtiene la información del destino desde el intent
        val destinationJson = intent.getStringExtra("destination")
        val destination = Gson().fromJson<Destination>(destinationJson, Destination::class.java)

        // Muestra la informacion en las vistas
        val tvName = findViewById<TextView>(R.id.tvDestinationName)
        val tvDescription = findViewById<TextView>(R.id.tvDestinationDescription)
        val tvCountry = findViewById<TextView>(R.id.tvCountry)
        val tvCategory = findViewById<TextView>(R.id.tvCategory)
        val tvPlan = findViewById<TextView>(R.id.tvPlan)
        val tvPrice = findViewById<TextView>(R.id.tvPrice)

        tvName.text = destination?.nombre
        tvDescription.text = destination?.plan
        tvCountry.text = "País: ${destination?.pais}"
        tvCategory.text = "Categoría: ${destination?.categoria}"
        tvPlan.text = "Plan: ${destination?.plan}"
        tvPrice.text = "Precio: ${destination?.precio}"

        // Añade a favoritos
        val btnAddToFavorites: Button = findViewById(R.id.btnAddToFavorites)
        btnAddToFavorites.setOnClickListener {
            addToFavorites(destination?.nombre)
        }
    }

    private fun addToFavorites(destinationName: String?) {
        if (destinationName != null) {
            // SharedPreferences, Base de Datos, u otras formas de almacenamiento
            // Solo se muestra un Toast
            Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
        }
    }
}
