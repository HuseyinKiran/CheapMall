package com.huseyinkiran.cheapmall.presentation.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.huseyinkiran.cheapmall.presentation.cart.CartViewModel
import com.huseyinkiran.cheapmall.presentation.navigation.Route

@Composable
fun FavoritesScreen(navController: NavController, modifier: Modifier) {

    val favoritesViewModel: FavoritesViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        favoritesViewModel.fetchFavorites()
        cartViewModel.getCart()
    }

    Box(modifier = modifier.fillMaxSize()) {
        FavoritesContent(
            favoritesViewModel = favoritesViewModel,
            cartViewModel = cartViewModel,
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope
        ) { product ->
            navController.currentBackStackEntry?.savedStateHandle?.set("product", product)
            navController.navigate(Route.ProductDetailScreen.route)
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

    }

}