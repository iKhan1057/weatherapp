package com.weatherapp.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_fav")
data class Favorite(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "city")
    val city:String,
    @ColumnInfo(name = "ccountry")
    val country:String
)
