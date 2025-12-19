package com.honoursigbeku.studyhubapp.ui.screens.authentication


sealed class AuthState {
    object Authenticated : AuthState()

    object Loading : AuthState()
    object Unauthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}