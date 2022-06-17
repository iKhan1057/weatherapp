package com.weatherapp.screens.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.model.Favorite
import com.weatherapp.repository.WeatherDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDatabaseRepository) :
    ViewModel() {
    private val _favlist = MutableStateFlow<List<Favorite>>(emptyList())
    val favlist = _favlist.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged().collect { listofFavs ->
                if (listofFavs.isNullOrEmpty()) {
                    Log.d("TAG", "NULL: ")
                } else {
                    _favlist.value = listofFavs
                    Log.d("TAG", "Values: $listofFavs")
                }
            }
        }
    }

    fun insertFavorite(favorite: Favorite) = viewModelScope.launch { repository.insertFavorite(favorite) }
    fun updateFavorite(favorite: Favorite) = viewModelScope.launch { repository.updateFavorite(favorite) }
    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch { repository.deleteFavorite(favorite) }
}