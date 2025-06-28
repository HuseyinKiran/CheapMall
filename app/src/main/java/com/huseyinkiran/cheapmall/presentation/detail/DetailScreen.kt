package com.huseyinkiran.cheapmall.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.huseyinkiran.cheapmall.presentation.cart.CartViewModel
import com.huseyinkiran.cheapmall.presentation.model.ProductUIModel

@Composable
fun DetailScreen(navController: NavController, modifier: Modifier, product: ProductUIModel) {

    val detailViewModel: DetailViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(product.id) {
        cartViewModel.getCart()
        detailViewModel.checkIfFavorite(product.id)
    }

    Box(modifier = modifier.fillMaxSize()) {
        DetailContent(
            product = product,
            detailViewModel = detailViewModel,
            cartViewModel = cartViewModel,
            snackbarHostState = snackbarHostState
        )
    }
}