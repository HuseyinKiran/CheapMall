package com.huseyinkiran.cheapmall.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huseyinkiran.cheapmall.data.repository.FirebaseAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: FirebaseAuthRepository
) : ViewModel() {

    private val _userEmail = MutableStateFlow("")
    val userEmail: StateFlow<String> = _userEmail

    val userUid = repository.currentUserUid

    init {
        getUserEmail()
    }

    fun signOut() = viewModelScope.launch {
        repository.signOut()
    }

    private fun getUserEmail() = viewModelScope.launch {
        _userEmail.value = repository.getUserEmail()
    }

}