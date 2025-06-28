package com.huseyinkiran.cheapmall.presentation.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.huseyinkiran.cheapmall.R
import com.huseyinkiran.cheapmall.presentation.navigation.Route

@Composable
fun AccountContent(navController: NavController, viewModel: AccountViewModel) {
    val userEmail by viewModel.userEmail.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = userEmail, fontWeight = FontWeight.Bold)
        Button(
            onClick = {
                viewModel.signOut()
                navController.navigate(Route.SignInScreen.route) {
                    popUpTo(0) { inclusive = true }
                }
            },
            modifier = Modifier,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(colorResource(R.color.orange))
        ) {
            Text(text = "Sign Out")
        }
    }
}