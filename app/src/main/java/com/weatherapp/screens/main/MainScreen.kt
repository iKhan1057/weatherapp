package com.weatherapp.screens.main

import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.weatherapp.data.DataOrException
import com.weatherapp.model.Weather
import com.weatherapp.navigation.WeatherScreens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.weatherapp.model.WeatherItem
import com.weatherapp.screens.settings.SettingsViewModel
import com.weatherapp.utils.formatDate
import com.weatherapp.utils.formatDecimals
import com.weatherapp.widgets.*

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {
    ShowData(mainViewModel = mainViewModel, settingsViewModel, navController, city)
}

@Composable
fun ShowData(
    mainViewModel: MainViewModel,
    settingsViewModel: SettingsViewModel,
    navController: NavController,
    city: String?
) {
    val curCity: String = if (city!!.isBlank()) "Kolkata" else city

    val unitFromDb = settingsViewModel.unitlist.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }
    if (!unitFromDb.isNullOrEmpty()) {
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"

        val weatherData =
            produceState<DataOrException<Weather, Boolean, Exception>>(
                initialValue = DataOrException(
                    loading = true
                )
            ) {
                value = mainViewModel.getWeatherData(city = curCity,units= unit)
            }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else {
            MainScffold(weather = weatherData.data!!, navController,isImperial)
        }
    }
}

@Composable
fun MainScffold(weather: Weather, navController: NavController,isImperial: Boolean) {

    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.city.name + " ,${weather.city.country}",
            navController = navController,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
            elevation = 5.dp
        ) {
            Log.d("TAG", "MainScaffold: Button Clicked")
        }
    }) {
        MainContent(
            data = weather,
            isImperial = isImperial
        )
    }
}

@Composable
fun MainContent(data: Weather, isImperial: Boolean) {
    val weatherItem = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"
    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = formatDate(weatherItem.dt), // Wed Nov 30
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals(weatherItem.temp.day) + "\u00B0",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = weatherItem.weather[0].main,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        HumidityWindPressureRow(weather = data.list[0], isImperial = isImperial)
        Divider()
        SunsetSunRiseRow(weather = data.list[0])
        Text(
            "This Week",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                items(items = data.list) { item: WeatherItem ->
                    WeatherDetailRow(weather = item)
                }
            }
        }
    }
}
