package com.huseyinkiran.cheapmall.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.huseyinkiran.cheapmall.presentation.account.AccountScreen
import com.huseyinkiran.cheapmall.presentation.cart.CartScreen
import com.huseyinkiran.cheapmall.presentation.detail.DetailScreen
import com.huseyinkiran.cheapmall.presentation.favorites.FavoritesScreen
import com.huseyinkiran.cheapmall.presentation.home.HomeScreen
import com.huseyinkiran.cheapmall.presentation.model.ProductUIModel
import com.huseyinkiran.cheapmall.presentation.signIn.SignInScreen
import com.huseyinkiran.cheapmall.presentation.signUp.SignUpScreen
import com.huseyinkiran.cheapmall.presentation.splash.SplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues
) {

    NavHost(
        navController = navController,
        startDestination = Route.SplashScreen.route,
    ) {
        composable(Route.SplashScreen.route) { SplashScreen(navController = navController) }
        composable(Route.SignInScreen.route) { SignInScreen(navController = navController) }
        composable(Route.SignUpScreen.route) { SignUpScreen(navController = navController) }
        composable(Route.HomeScreen.route) {
            HomeScreen(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
        composable(
            Route.ProductDetailScreen.route,
        ) {
            val product = remember {
                navController.previousBackStackEntry?.savedStateHandle?.get<ProductUIModel>("product")
            }
            if (product != null) {
                DetailScreen(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding),
                    product = product
                )
            }
        }
        composable(Route.CartScreen.route) {
            CartScreen(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
        composable(Route.FavoritesScreen.route) {
            FavoritesScreen(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
        composable(Route.AccountScreen.route) { AccountScreen(navController = navController) }
    }
}