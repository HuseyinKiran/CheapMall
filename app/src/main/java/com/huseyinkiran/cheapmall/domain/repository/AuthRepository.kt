package com.huseyinkiran.cheapmall.domain.repository

import com.huseyinkiran.cheapmall.domain.model.Person
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    suspend fun registerUser(name: String, email: String, password: String): Result<Person>
    suspend fun signInUser(email: String, password: String): Result<Person>
    suspend fun signOut()
    suspend fun getUserEmail(): String
    val currentUserUid: StateFlow<String?>
}