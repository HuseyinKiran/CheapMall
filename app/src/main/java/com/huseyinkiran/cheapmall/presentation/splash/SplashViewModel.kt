package com.huseyinkiran.cheapmall.presentation.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.huseyinkiran.cheapmall.presentation.navigation.SplashDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _destination = mutableStateOf<SplashDestination?>(null)
    val destination: State<SplashDestination?> = _destination

    init {
        initiateSplashFlow()
    }

    private fun initiateSplashFlow() = viewModelScope.launch {
        delay(1500)
        _destination.value = if (auth.currentUser != null) {
            SplashDestination.HOME
        } else {
            SplashDestination.SIGN_IN
        }
    }

}