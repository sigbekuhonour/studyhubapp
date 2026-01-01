package com.honoursigbeku.studyhubapp.ui.screens.authentication


sealed class AuthState {
    data class Authenticated(val userId: String) : AuthState()

    object Loading : AuthState()
    object Unauthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}