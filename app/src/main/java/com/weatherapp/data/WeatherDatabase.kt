package com.weatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weatherapp.model.Favorite
import com.weatherapp.model.WeatherUnit

@Database(entities = [Favorite::class,WeatherUnit::class], exportSchema = false, version = 2)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}