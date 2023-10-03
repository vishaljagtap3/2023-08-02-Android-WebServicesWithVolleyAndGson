package com.bitcodetech.webservices2

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object Util {

    lateinit var requestQueue : RequestQueue

    fun init(context: Context) {
        requestQueue = Volley.newRequestQueue(context)
    }

    val placesApiUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?keyword=hotels&location=18.5204,73.8567&radius=1500&type=restaurant&key=AIzaSyDKMUzdmKS_pRfm7ACcpo5yNcNP_1fHoDM"

    fun getPlaces() : ArrayList<Place>? {

        val httpCon =
        URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?keyword=hotels&location=18.5204,73.8567&radius=1500&type=restaurant&key=AIzaSyDKMUzdmKS_pRfm7ACcpo5yNcNP_1fHoDM")
            .openConnection() as HttpURLConnection

        httpCon.connect()

        if(httpCon.responseCode != 200) {
            return null
        }

        val responseBuff = StringBuffer()
        val data = ByteArray(1024)
        var count = 0

        count = httpCon.inputStream.read(data)
        while(count != -1) {
            responseBuff.append(String(data, 0, count))
            count = httpCon.inputStream.read(data)
        }

        val jRes = JSONObject(responseBuff.toString())
        val status = jRes.getString("status")

        val jPlaces = jRes.getJSONArray("results")
        val places = ArrayList<Place>()

        for(i in 0..jPlaces.length() - 1) {
            val jPlace = jPlaces.getJSONObject(i)

            val name = jPlace.getString("name")
            val address = jPlace.getString("vicinity")
            val raring = jPlace.getDouble("rating")
            val icon = jPlace.getString("icon")
            val jGeometry = jPlace.getJSONObject("geometry")
            val jLocation = jGeometry.getJSONObject("location")
            val lat = jLocation.getDouble("lat")
            val lng = jLocation.getDouble("lng")

            places.add(
                Place(
                    name,
                    lat, lng,
                    raring,
                    address,
                    icon
                )
            )
        }

        return places

    }

    fun getPlacesNew() : Places? {

        val httpCon =
            URL("https://maps.googleapis.com/maps/api/place/nearbysearch/json?keyword=hotels&location=18.5204,73.8567&radius=1500&type=restaurant&key=AIzaSyDKMUzdmKS_pRfm7ACcpo5yNcNP_1fHoDM")
                .openConnection() as HttpURLConnection

        httpCon.connect()

        if(httpCon.responseCode != 200) {
            return null
        }

        val responseBuff = StringBuffer()
        val data = ByteArray(1024)
        var count = 0

        count = httpCon.inputStream.read(data)
        while(count != -1) {
            responseBuff.append(String(data, 0, count))
            count = httpCon.inputStream.read(data)
        }

        val gson = Gson()

        val places = gson.fromJson(
                responseBuff.toString(),
                Places::class.java
        )

        return places

    }

    /*fun getPlacesNewUsingVolley() : Places? {


        val gson = Gson()

        val places = gson.fromJson(
            responseBuff.toString(),
            Places::class.java
        )

        return places

    }*/

    fun stringRequestDemo(context : Context) {

        //val requestQueue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET,
            placesApiUrl,
            object : Response.Listener<String> {
                override fun onResponse(response: String?) {
                    Log.e("tag", "####################################")
                    Log.e("tag", "$response")

                    val gson = Gson()

                    val places = gson.fromJson(
                        response,
                        Places::class.java
                    )

                    Log.e("tag", "${places.results}")

                    Log.e("tag", "####################################")
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Log.e("tag", "####################################")
                    Log.e("tag", "Error")
                    Log.e("tag", "####################################")

                }
            }
        )

        requestQueue.add(stringRequest)
    }

    fun jsonObjectRequestDemo(context : Context) {

        //val requestQueue = Volley.newRequestQueue(context)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            placesApiUrl,
            null,
            object : Response.Listener<JSONObject> {
                override fun onResponse(response: JSONObject?) {
                    Log.e("tag", "####################################")
                    Log.e("tag", "$response")

                    val gson = Gson()

                    val places = gson.fromJson(
                        response.toString(),
                        Places::class.java
                    )

                    Log.e("tag", "${places.results}")

                    Log.e("tag", "####################################")
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Log.e("tag", "####################################")
                    Log.e("tag", "Error")
                    Log.e("tag", "####################################")

                }
            }
        )

        requestQueue.add(jsonObjectRequest)
    }

}