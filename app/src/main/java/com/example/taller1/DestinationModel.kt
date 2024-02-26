package com.example.taller1

data class DestinationModel(
    val destinos: List<Destination>
)
data class Destination(
    val id: Int,
    val nombre: String,
    val pais: String,
    val categoria: String,
    val plan: String,
    val precio: Double
)
