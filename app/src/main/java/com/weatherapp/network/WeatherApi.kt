package com.weatherapp.network

import com.weatherapp.model.Weather
import com.weatherapp.model.WeatherObject
import com.weatherapp.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query : String,
        @Query("appid") appid: String = API_KEY,
        @Query("units") units: String = "imperial"
    ): Weather
}