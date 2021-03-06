package com.weatherapp.screens.splash


import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.weatherapp.R
import com.weatherapp.model.WeatherUnit
import com.weatherapp.navigation.WeatherScreens
import com.weatherapp.screens.settings.SettingsViewModel
import com.weatherapp.ui.theme.Purple500
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@Composable
fun WeatherSplashScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val defaultCity = "Kolkata"
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true, block = {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                })
        )
        settingsViewModel.insertWeatherUnit(WeatherUnit(unit = "Imperial (F)"))
        delay(2000L)
        navController.navigate(WeatherScreens.MainScreen.name + "/${defaultCity}")
    })

    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp)
            .scale(scale.value),
        shape = CircleShape,
        color = Color(0xFF673AB7),
        border = BorderStroke(
            width = 2.dp, color = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.sun),
                contentDescription = "sunny icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(95.dp),
            )
            Text(
                text = "Find the Sun?",
                style = MaterialTheme.typography.h5,
                color = Color.White
            )
        }
    }

}