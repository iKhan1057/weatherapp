package com.weatherapp.screens.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.weatherapp.model.Favorite
import com.weatherapp.navigation.WeatherScreens
import com.weatherapp.widgets.WeatherAppBar

@Composable
fun FavoriteScreen(navController: NavController, favoriteViewModel: FavoriteViewModel= hiltViewModel()) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Favorite",
            icon = Icons.Default.ArrowBack,
            isMainScreen = false,
            navController = navController
        ) { navController.popBackStack() }
    }) {
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                val list = favoriteViewModel.favlist.collectAsState().value
                LazyColumn() {
                    items(items = list) {
                        FavRow(
                            favorite = it,
                            navController = navController,
                            favoriteViewModel = favoriteViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FavRow(favorite: Favorite, navController: NavController, favoriteViewModel: FavoriteViewModel) {
    Surface(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(WeatherScreens.MainScreen.name + "/${favorite.city}")
            },
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color(0xFD30F7A)
    ) {
        Row(
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = favorite.city, modifier = Modifier.padding(start = 4.dp))

            Surface(
                modifier = Modifier.padding(2.dp),
                color = Color.LightGray,
                shape = CircleShape
            ) {
                Text(
                    text = favorite.country,
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.caption
                )
            }

            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete Favorite",
                modifier = Modifier
                    .padding(2.dp)
                    .clickable {
                       favoriteViewModel.deleteFavorite(favorite)
                    },
                tint = Color.Red
            )
        }
    }
}
