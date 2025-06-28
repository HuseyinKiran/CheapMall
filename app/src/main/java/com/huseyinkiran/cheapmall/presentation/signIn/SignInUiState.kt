package com.huseyinkiran.cheapmall.presentation.signIn

import com.huseyinkiran.cheapmall.domain.model.Person

sealed class SignInUiState {
    data object Idle: SignInUiState()
    data object Loading: SignInUiState()
    data class Success(val person: Person): SignInUiState()
    data class Error(val message: String): SignInUiState()
}