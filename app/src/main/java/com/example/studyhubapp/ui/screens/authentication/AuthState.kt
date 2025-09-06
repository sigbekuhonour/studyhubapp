package com.example.studyhubapp.ui.screens.authentication


sealed class AuthState {
    object Authenticated : AuthState()

    //(val user: User)
    object Unauthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}