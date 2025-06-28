package com.huseyinkiran.cheapmall.presentation.signUp

import com.huseyinkiran.cheapmall.domain.model.Person

sealed class SignUpUiState {
    data object Idle: SignUpUiState()
    data object Loading: SignUpUiState()
    data class Success(val person: Person): SignUpUiState()
    data class Error(val message: String): SignUpUiState()
}