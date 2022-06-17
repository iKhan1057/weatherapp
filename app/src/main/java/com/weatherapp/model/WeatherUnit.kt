package com.weatherapp.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_settings")
data class WeatherUnit(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "unit") val unit:String
)
