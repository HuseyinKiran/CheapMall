package com.huseyinkiran.cheapmall.presentation.account

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun AccountScreen(navController: NavController) {

    val viewModel: AccountViewModel = hiltViewModel()

    AccountContent(navController = navController, viewModel = viewModel)

}