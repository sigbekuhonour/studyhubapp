package com.example.studyhubapp.ui.screens.authentication


sealed class AuthState {
    object Authenticated : AuthState()

    //(val user: User)
    object UnAuthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}