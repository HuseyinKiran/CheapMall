package com.huseyinkiran.cheapmall.presentation.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.huseyinkiran.cheapmall.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<SignInUiState>(SignInUiState.Idle)
    val authState: StateFlow<SignInUiState> = _authState.asStateFlow()

    fun resetState() {
        _authState.value = SignInUiState.Idle
    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        _authState.value = SignInUiState.Loading
        try {

            val result = authRepository.signInUser(email, password)
            _authState.value = SignInUiState.Success(result.getOrThrow())
        } catch (e: Exception) {
            val message = when (e) {
                is FirebaseAuthInvalidUserException -> "User not found"
                is FirebaseAuthInvalidCredentialsException -> "Please check your password"
                is FirebaseFirestoreException -> "An error occurred while retrieving data"
                else -> e.localizedMessage ?: "Unknown error"
            }
            _authState.value = SignInUiState.Error(message)
        }
    }

}