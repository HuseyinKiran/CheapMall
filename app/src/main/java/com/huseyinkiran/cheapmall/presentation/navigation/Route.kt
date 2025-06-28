package com.huseyinkiran.cheapmall.presentation.navigation

sealed class Route(val route: String) {
    data object SplashScreen: Route("splash")
    data object SignUpScreen: Route("signUp")
    data object SignInScreen: Route("signIn")
    data object HomeScreen: Route("home")
    data object ProductDetailScreen : Route("product")
    data object CartScreen: Route("cart")
    data object FavoritesScreen: Route("favorites")
    data object AccountScreen: Route("account")
}