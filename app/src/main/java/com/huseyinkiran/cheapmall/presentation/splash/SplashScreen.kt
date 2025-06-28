package com.huseyinkiran.cheapmall.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.huseyinkiran.cheapmall.R
import com.huseyinkiran.cheapmall.presentation.navigation.Route
import com.huseyinkiran.cheapmall.presentation.navigation.SplashDestination

@Composable
fun SplashScreen(navController: NavController) {

    val viewModel: SplashViewModel = hiltViewModel()
    val destination by viewModel.destination

    LaunchedEffect(destination) {
        when(destination) {
            SplashDestination.HOME -> {
                navController.navigate(Route.HomeScreen.route) {
                    popUpTo(Route.SplashScreen.route) { inclusive = true }
                }
            }

            SplashDestination.SIGN_IN -> {
                navController.navigate(Route.SignInScreen.route) {
                    popUpTo(Route.SplashScreen.route) { inclusive = true }
                }
            }

            null -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.wallpaper),
            contentDescription = "Splash Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }

}