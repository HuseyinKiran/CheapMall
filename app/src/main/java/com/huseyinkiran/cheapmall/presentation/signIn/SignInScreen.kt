package com.huseyinkiran.cheapmall.presentation.signIn

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.huseyinkiran.cheapmall.presentation.navigation.Route

@Composable
fun SignInScreen(navController: NavController) {

    val viewModel: SignInViewModel = hiltViewModel()
    val authState = viewModel.authState.collectAsState()
    val state = authState.value

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state) {
        when (state) {
            is SignInUiState.Success -> {
                navController.navigate(Route.HomeScreen.route) {
                    popUpTo(0) { inclusive = true }
                }
                viewModel.resetState()
            }

            is SignInUiState.Error -> {
                snackbarHostState.showSnackbar(state.message)
                viewModel.resetState()
            }

            else -> {}
        }
    }

    SignInContent(
        navController = navController,
        viewModel = viewModel,
        snackbarHostState = snackbarHostState,
        state = state
    )

}