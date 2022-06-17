package com.weatherapp.repository

import android.util.Log
import com.weatherapp.data.DataOrException
import com.weatherapp.model.Weather
import com.weatherapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(
        cityname: String,
        units: String
    ): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityname, units = units)
        } catch (execption: Exception) {
            Log.d("REX", "getWeather: $execption")
            return DataOrException(e = execption)
        }
        return DataOrException(data = response)
    }
}