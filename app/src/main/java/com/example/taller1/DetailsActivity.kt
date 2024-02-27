package com.example.taller1

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taller1.utils.APIUtil
import com.google.gson.Gson
import android.graphics.Color
import android.view.View
import kotlin.properties.Delegates

class DetailsActivity : AppCompatActivity() {

    private lateinit var destination: Destination
    private lateinit var btnAddToFavorites : Button
    private var foundFavorites : Boolean = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Obtiene la información del destino desde el intent
        val destinationJson = intent.getStringExtra("destination")
        destination = Gson().fromJson<Destination>(destinationJson, Destination::class.java)

        val api = APIUtil()
        api.init(this)
        val queries : HashMap<String, String> = HashMap()
        val headers : HashMap<String, String> = HashMap()
        queries["key"] = BuildConfig.API_KEY
        queries["q"] = destination.pais
        headers["Content-Type"] = "application/json"

        val celcius = findViewById<TextView>(R.id.tvCelcius)
        val celciusFeel = findViewById<TextView>(R.id.tvCelciusFeel)

        api.makeAPICall(
            null,
            queries,
            headers,
            "https://api.weatherapi.com/v1/current.json",
            {
                Log.i("REQUEST SUCCESS", it.toString())
                celcius.text = "Temperatura C° ${it.current.temp_c}"
                celciusFeel.text = "Sensación termica C° ${it.current.feelslike_c}"
            },
            {
                Log.i("REQUEST Failed", "NOOOOO")
            }
        )

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
        btnAddToFavorites= findViewById(R.id.btnAddToFavorites)
        foundFavorites = FavoritesStore.searchFavorite(destination.id)
        toggleFavoriteMode()
        btnAddToFavorites.setOnClickListener {
            addToFavorites(destination.nombre)
        }
    }

    private fun addToFavorites(destinationName: String?) {
        if (destinationName != null && !foundFavorites) {
            if (FavoritesStore.addFavorite(destination)) {
                Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
                foundFavorites = true
                toggleFavoriteMode()
            }
            else Toast.makeText(this, "Sucedió algo añadiendo el favorito", Toast.LENGTH_SHORT).show()

        } else if (foundFavorites) {
            if (FavoritesStore.deleteFavorite(destination.id)) {
                Toast.makeText(this, "Tu favorito fue eliminado", Toast.LENGTH_SHORT).show()
                foundFavorites = false
                toggleFavoriteMode()
            }
            else Toast.makeText(this, "Sucedió algo eliminando el favorito", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleFavoriteMode() {
        Log.i("FAV", foundFavorites.toString())
        if (foundFavorites) {
            btnAddToFavorites.setBackgroundColor(Color.RED)
            btnAddToFavorites.text = "Eliminar de favoritos"
        } else {
            btnAddToFavorites.setBackgroundColor(Color.GREEN)
            btnAddToFavorites.text = "Agregar a favoritos"
        }
    }
}
