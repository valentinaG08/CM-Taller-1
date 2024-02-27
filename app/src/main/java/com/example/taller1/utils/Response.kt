package com.example.taller1.utils

import org.json.JSONObject

data class LocationResponse(
    val name: String,
    val region: String,
    val country: String,
    val lat: Float,
    val lon: Float,
    val tz_id: String,
    val localtime_epoch: Number,
    val localtime: String
)

data class ConditionResponse(
    val text: String,
    val icon: String,
    val code: String
)

data class CurrentResponse(
    val last_updated_epoch : Number,
    val last_updated : String,
    val temp_c : Float,
    val temp_f : Float,
    val is_day : Number,
    val condition : ConditionResponse,
    val wind_mph : Float,
    val wind_kph : Float,
    val wind_degree : Float,
    val wind_dir : String,
    val pressure_mb : Float,
    val pressure_in : Float,
    val precip_mm : Float,
    val precip_in : Float,
    val humidity : Number,
    val cloud : Number,
    val feelslike_c : Float,
    val feelslike_f : Float,
    val vis_km : Float,
    val vis_miles : Float,
    val uv : Float,
    val gust_mph : Float,
    val gust_kph : Float
)

data class Response (
    val location: LocationResponse,
    val current: CurrentResponse
)