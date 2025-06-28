package com.huseyinkiran.cheapmall.presentation.signUp

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.huseyinkiran.cheapmall.presentation.navigation.Route

@Composable
fun SignUpScreen(navController: NavController) {

    val viewModel: SignUpViewModel = hiltViewModel()
    val authState = viewModel.authState.collectAsState()
    val state = authState.value

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state) {
        when (state) {
            is SignUpUiState.Success -> {
                snackbarHostState.showSnackbar(message = "Welcome ${state.person.name}")
                navController.navigate(Route.SignInScreen.route) {
                    popUpTo(Route.SignUpScreen.route) { inclusive = true }
                    launchSingleTop = true
                }
                viewModel.resetState()
            }

            is SignUpUiState.Error -> {
                snackbarHostState.showSnackbar(state.message)
                viewModel.resetState()
            }

            else -> {}
        }
    }

    SignUpContent(viewModel = viewModel, snackbarHostState = snackbarHostState, state = state)

}