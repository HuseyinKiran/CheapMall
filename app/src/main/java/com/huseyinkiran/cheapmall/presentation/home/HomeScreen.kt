package com.huseyinkiran.cheapmall.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.huseyinkiran.cheapmall.R
import com.huseyinkiran.cheapmall.presentation.navigation.Route

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier) {

    val viewModel: HomeViewModel = hiltViewModel()
    val state = viewModel.productsState.value

    Box(modifier = modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center), color = colorResource(
                        R.color.orange
                    )
                )
            }

            state.errorMessage != null -> {
                Text(text = state.errorMessage, modifier = Modifier.align(Alignment.Center))
            }

            else -> {
                HomeContent(products = state.products) { product ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("product", product)
                    navController.navigate(Route.ProductDetailScreen.route)
                }
            }

        }
    }

}