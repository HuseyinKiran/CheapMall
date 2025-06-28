package com.huseyinkiran.cheapmall.core

object InputValidator {

    fun validateSignUpInputs(name: String, email: String, password: String, confirmPassword: String): String? {
        return when {
            name.isBlank() -> "Please enter your name"
            email.isBlank() -> "Please enter your email"
            password.isBlank() -> "Please enter your password"
            confirmPassword.isBlank() -> "Please confirm your password"
            password != confirmPassword -> "Passwords do not match"
            else -> null
        }
    }

    fun validateSignInInputs(email: String, password: String): String? {
        return when {
            email.isBlank() -> "Please enter your email"
            password.isBlank() -> "Please enter your password"
            else -> null
        }
    }

}