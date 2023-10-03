package com.bitcodetech.webservices2

import android.util.Log

class MyThread : Thread() {

    override fun run() {
        /*val places = Util.getPlaces() ?: return

        for(place in places!!) {
            Log.e("tag", place.toString())
        }*/

        val places = Util.getPlacesNew()
        Log.e("tag", "******************************")

        Log.e("tag", "${places!!.status}")

        for(business in places.results) {
            Log.e("tag", business.toString())
        }

        Log.e("tag", "******************************")
    }
}