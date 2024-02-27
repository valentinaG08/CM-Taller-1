package com.example.taller1.utils

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class APIUtil {

    private lateinit var apiRequestQueue : RequestQueue;

    fun init(context: Context) {
        apiRequestQueue = Volley.newRequestQueue(context);
    }

    fun makeAPICall(
        body: HashMap<String, String>?,
        queries: HashMap<String, String>?,
        headers: HashMap<String, String>?,
        url: String,
        onSuccess: (R: Response) -> Unit,
        onError: () -> Unit
    ) {
        var urlString = url
        val payload = JSONObject()
        if (!body.isNullOrEmpty()) for ((key, value) in body) payload.put(key, value)
        if (!queries.isNullOrEmpty()) {
            urlString += "?"
            for ((key, value) in queries) urlString += "$key=$value&"

            urlString = urlString.removeSuffix("&")
        }
        Log.i("URL", urlString)
        val request = GsonRequest(
            url = urlString,
            clazz = Response::class.java,
            method = Request.Method.GET,
            headers = headers,
            jsonPayload = payload,
            listener = {
                Log.i("RES BIND", it.toString())
                onSuccess(it)
            },
            errorListener = {
                Log.i("ERROR", it.message?: "No hay mensaje")
                onError()
            }
        )

        apiRequestQueue.add(request)
    }
}