package com.weatherapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.weatherapp.model.Favorite
import com.weatherapp.model.WeatherUnit
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query(value = "SELECT * FROM tbl_fav")
    fun getFavorites(): Flow<List<Favorite>>

    @Query(value = "SELECT * FROM tbl_fav WHERE city =:city")
    suspend fun getFavById(city: String): Favorite

    @Insert(onConflict = REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Insert(onConflict = REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Query(value = "DELETE FROM tbl_fav")
    suspend fun deleteAllFavorite()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)



    @Query(value = "SELECT * FROM tbl_settings")
    fun getWeatherUnits(): Flow<List<WeatherUnit>>

    @Insert(onConflict = REPLACE)
    suspend fun insertWeatherUnits(weatherUnit: WeatherUnit)

    @Insert(onConflict = REPLACE)
    suspend fun updateWeatherUnits(weatherUnit: WeatherUnit)

    @Query(value = "DELETE FROM tbl_settings")
    suspend fun deleteAllWeatherUnits()

    @Delete
    suspend fun deleteWeatherUnits(weatherUnit: WeatherUnit)
}