package com.huseyinkiran.cheapmall.presentation.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.huseyinkiran.cheapmall.R
import com.huseyinkiran.cheapmall.core.InputValidator
import kotlinx.coroutines.launch


@Composable
fun SignUpContent(
    viewModel: SignUpViewModel,
    snackbarHostState: SnackbarHostState,
    state: SignUpUiState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        var name by remember { mutableStateOf("") }
        var mail by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var pwIsVisible by remember { mutableStateOf(false) }
        var cpwIsVisible by remember { mutableStateOf(false) }

        val coroutineScope = rememberCoroutineScope()

        Image(
            painter = painterResource(R.drawable.wallpaper),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 16.dp)
        ) {

            Card(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .wrapContentSize()
                    .padding(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Text(
                    text = "Sign Up",
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = colorResource(R.color.orange),
                    fontWeight = FontWeight.SemiBold
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Name") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Person Icon")
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = mail,
                    onValueChange = { mail = it },
                    label = { Text(text = "Mail") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email Icon"
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Password") },
                    visualTransformation = if (pwIsVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            pwIsVisible = !pwIsVisible
                        }) {
                            Icon(
                                imageVector = if (pwIsVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (pwIsVisible) "VisibilityOff Icon" else "Visibility Icon"
                            )
                        }
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Lock Icon"
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text(text = "Password") },
                    visualTransformation = if (cpwIsVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            cpwIsVisible = !cpwIsVisible
                        }) {
                            Icon(
                                imageVector = if (cpwIsVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (cpwIsVisible) "VisibilityOff Icon" else "Visibility Icon"
                            )
                        }
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Lock Icon"
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val error = InputValidator.validateSignUpInputs(
                            name,
                            mail,
                            password,
                            confirmPassword
                        )
                        if (error != null) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(error)
                            }
                        } else {
                            viewModel.signUp(name, mail, password)
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(colorResource(R.color.orange)),
                    shape = RoundedCornerShape(4.dp),
                    enabled = state is SignUpUiState.Idle,
                ) {
                    Text(text = "Sign Up", color = Color.White)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        if (state is SignUpUiState.Loading || state is SignUpUiState.Success) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = colorResource(R.color.orange)
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

    }
}