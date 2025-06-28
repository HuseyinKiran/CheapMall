package com.huseyinkiran.cheapmall.presentation.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.huseyinkiran.cheapmall.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val authState: StateFlow<SignUpUiState> = _authState.asStateFlow()

    fun resetState() {
        _authState.value = SignUpUiState.Idle
    }

    fun signUp(name: String, email: String, password: String) = viewModelScope.launch {

        _authState.value = SignUpUiState.Loading
        try {
            val person = authRepository.registerUser(name, email, password)
            _authState.value = SignUpUiState.Success(person.getOrThrow())
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            val message = when (e) {
                is FirebaseAuthWeakPasswordException -> "Password is too weak"
                is FirebaseAuthUserCollisionException -> "This email is already registered"
                is FirebaseAuthInvalidCredentialsException -> "Invalid email format"
                else -> e.localizedMessage ?: "Unknown error"
            }
            _authState.value = SignUpUiState.Error(message)
        }

    }

}