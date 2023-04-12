package com.example.earthquakemonitor.main

import com.example.earthquakemonitor.Earthquake
import com.example.earthquakemonitor.api.EarthquakeJsonResponse
import com.example.earthquakemonitor.api.EarthquakesApi
import com.example.earthquakemonitor.database.EqDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val database: EqDatabase) {
    val eqList = database.eqDao.getEarthquakes()

    suspend fun fetchEqList() {
        return withContext(Dispatchers.IO) {
            val earthquakeJsonResponse =
                EarthquakesApi.retrofitService.getLastHourEarthquakes()
            val earthquakes = getEarthquakeListFromResponse(earthquakeJsonResponse)
            database.eqDao.insertAll(earthquakes)
        }
    }

    private fun getEarthquakeListFromResponse(earthquakeJsonResponse: EarthquakeJsonResponse):
            MutableList<Earthquake> {
        val eqList = mutableListOf<Earthquake>()

        val features = earthquakeJsonResponse.features
        for (feature in features) {
            val id = feature.id
            val place = feature.properties.place
            val magnitude = feature.properties.mag
            val time = feature.properties.time
            val latitude = feature.geometry.latitude
            val longitude = feature.geometry.longitude
            val earthquake = Earthquake(id, place, magnitude, time, latitude, longitude)
            eqList.add(earthquake)
        }

        return eqList
    }
}