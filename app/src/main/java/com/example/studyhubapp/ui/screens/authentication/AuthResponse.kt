package com.example.studyhubapp.ui.screens.authentication

interface AuthResponse {
    object Success : AuthResponse
    data class Error(val message: String) : AuthResponse
}