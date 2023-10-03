package com.bitcodetech.webservices2

import com.google.gson.annotations.SerializedName

class Places {
    val status : String
    val results : ArrayList<Business>

    init {
        status = ""
        results = ArrayList()
    }


}

data class Business (
    val icon : String,
    @SerializedName("vicinity")
    val address : String,
    val rating : Double,
    val name : String,
    val geometry : Geometry
)

data class Geometry(
    val location : BLocation
)

data class BLocation (
    val lat : Double,
    val lng : Double
)