package com.weatherapp.screens.main


import androidx.lifecycle.ViewModel
import com.weatherapp.data.DataOrException
import com.weatherapp.model.Weather
import com.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository)
    : ViewModel(){
    suspend fun getWeatherData(city: String, units: String)
            : DataOrException<Weather, Boolean, Exception> {
        return repository.getWeather(city,units)
    }
}