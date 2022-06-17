package com.weatherapp.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.model.Favorite
import com.weatherapp.model.WeatherUnit
import com.weatherapp.repository.WeatherDatabaseRepository
import com.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val weatherRepository: WeatherDatabaseRepository) :
    ViewModel() {
    private val _unitlist = MutableStateFlow<List<WeatherUnit>>(emptyList())
    val unitlist = _unitlist.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getWeatherUnits().collect { listOFUnits ->
                if (listOFUnits.isNullOrEmpty()) {
                    Log.d("TAG", "NULL: ")
                } else {
                    _unitlist.value = listOFUnits
                    Log.d("TAG", "Values: $listOFUnits")
                }
            }
        }
    }

    fun insertWeatherUnit(weatherUnit: WeatherUnit) =
        viewModelScope.launch { weatherRepository.insertWeatherUnits(weatherUnit) }

    fun updateWeatherUnit(weatherUnit: WeatherUnit) =
        viewModelScope.launch { weatherRepository.updateWeatherUnits(weatherUnit) }

    fun deleteWeatherUnit(weatherUnit: WeatherUnit) =
        viewModelScope.launch { weatherRepository.deleteWeatherUnits(weatherUnit) }

    fun deleteAllWeatherUnits() =
        viewModelScope.launch { weatherRepository.deleteAllWeatherUnits() }
}