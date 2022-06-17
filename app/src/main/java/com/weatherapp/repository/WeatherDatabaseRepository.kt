package com.weatherapp.repository

import com.weatherapp.data.WeatherDao
import com.weatherapp.model.Favorite
import com.weatherapp.model.WeatherUnit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class WeatherDatabaseRepository @Inject constructor(private val weatherDao: WeatherDao) {
    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteAllFavorite() = weatherDao.deleteAllFavorite()
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun getFavById(city: String): Favorite = weatherDao.getFavById(city)


    fun getWeatherUnits(): Flow<List<WeatherUnit>> = weatherDao.getWeatherUnits()
    suspend fun insertWeatherUnits(weatherUnit: WeatherUnit) = weatherDao.insertWeatherUnits(weatherUnit)
    suspend fun updateWeatherUnits(weatherUnit: WeatherUnit) = weatherDao.updateWeatherUnits(weatherUnit)
    suspend fun deleteAllWeatherUnits() = weatherDao.deleteAllWeatherUnits()
    suspend fun deleteWeatherUnits(weatherUnit: WeatherUnit) = weatherDao.deleteWeatherUnits(weatherUnit)

}