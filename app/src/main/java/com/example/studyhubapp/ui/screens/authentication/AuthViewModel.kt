package com.example.studyhubapp.ui.screens.authentication

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studyhubapp.data.repository.AuthRepositoryImpl
import com.example.studyhubapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.UnAuthenticated)

    //
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun updateAuthStatus(authStatus: AuthState) {
        _authState.value = authStatus
    }

    fun signUpWithEmail(email: String, password: String) {
        viewModelScope.launch {
            try {
                authRepository.signUpWithEmail(
                    email = email,
                    password = password
                ).collect { response ->
                    when (response) {
                        is AuthResponse.Success -> {
                            _authState.value = AuthState.Authenticated
                        }

                        is AuthResponse.Error -> {
                            _authState.value = AuthState.Error(response.message)
                            Log.e("AuthViewModel", "Sign up with Email failed: ${response.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Unexpected error occurred")
                Log.e("AuthViewModel", "Sign up with Email exception", e)
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            try {
                authRepository.signInWithEmail(
                    email = email,
                    password = password
                ).collect { response ->
                    when (response) {
                        is AuthResponse.Success -> {
                            _authState.value = AuthState.Authenticated
                        }

                        is AuthResponse.Error -> {
                            _authState.value = AuthState.Error(response.message)
                            Log.e("AuthViewModel", "Sign in with Email failed: ${response.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Unexpected error occurred")
                Log.e("AuthViewModel", "Sign in with Email exception", e)
            }
        }
    }
    
    fun signInWithGoogle(context: Context, serverClientId: String) {
        viewModelScope.launch {
            try {
                authRepository.signInWithGoogle(context = context, serverClientId = serverClientId)
                    .collect { response ->
                        when (response) {
                            is AuthResponse.Success -> {
                                _authState.value = AuthState.Authenticated
                            }

                            is AuthResponse.Error -> {
                                _authState.value = AuthState.Error(response.message)
                                Log.e(
                                    "AuthViewModel",
                                    "Sign in with Google failed: ${response.message}"
                                )
                            }
                        }
                    }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Unexpected error occurred")
                Log.e("AuthViewModel", "Sign in with Google exception", e)
            }

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AuthViewModel(
                    authRepository = AuthRepositoryImpl()
                )
            }
        }
    }
}


