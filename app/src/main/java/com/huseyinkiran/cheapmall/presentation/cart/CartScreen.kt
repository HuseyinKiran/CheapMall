package com.huseyinkiran.cheapmall.presentation.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.huseyinkiran.cheapmall.presentation.navigation.Route

@Composable
fun CartScreen(navController: NavController, modifier: Modifier) {

    val viewModel: CartViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.getCart()
        viewModel.getTotalAmount()
    }

    Box(modifier = modifier.fillMaxSize()) {
        CartContent(viewModel = viewModel) { product ->
            navController.currentBackStackEntry?.savedStateHandle?.set("product", product)
            navController.navigate(Route.ProductDetailScreen.route)
        }
    }

}