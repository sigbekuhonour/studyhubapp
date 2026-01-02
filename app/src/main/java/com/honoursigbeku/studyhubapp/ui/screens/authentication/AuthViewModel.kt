package com.honoursigbeku.studyhubapp.ui.screens.authentication

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.honoursigbeku.studyhubapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    val authState: StateFlow<AuthState> = authRepository.authState()

    fun signUpWithEmail(email: String, password: String) {
        viewModelScope.launch {
            try {
                authRepository.signUpWithEmail(
                    email = email, password = password
                ).collect { response ->
                    when (response) {
                        is AuthResponse.Success -> {
                            Log.d(
                                "AuthViewModel",
                                "Success response received when signing up with email"
                            )
                            authRepository.saveUser()
                            Log.e(
                                "AuthViewModel",
                                "successfully saved user to supabase"
                            )
                            authRepository.onboardUser()
                        }

                        is AuthResponse.Error -> {
                            Log.e("AuthViewModel", "Sign up with Email failed: ${response.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Sign up with Email exception", e)
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            try {
                authRepository.signInWithEmail(
                    email = email, password = password
                ).collect { response ->
                    when (response) {
                        is AuthResponse.Success -> {
                            Log.d(
                                "AuthViewModel",
                                "Success response received when signing in with email"
                            )
                            authRepository.onboardUser()
                            Log.d(
                                "AuthViewModel",
                                "User onboarding completed"
                            )
                        }

                        is AuthResponse.Error -> {
                            Log.e("AuthViewModel", "Sign in with Email failed: ${response.message}")
                        }
                    }
                }
            } catch (e: Exception) {
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
                                authRepository.saveUser()
                                Log.d(
                                    "AuthViewModel",
                                    "Success response received when signing in with google"
                                )
                                authRepository.onboardUser()
                                Log.d(
                                    "AuthViewModel",
                                    "User onboarding completed"
                                )
                            }

                            is AuthResponse.Error -> {
                                Log.e(
                                    "AuthViewModel",
                                    "Sign in with Google failed: ${response.message}"
                                )
                            }
                        }
                    }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Sign in with Google exception", e)
            }

        }
    }

    fun signOut() {
        viewModelScope.launch {
            when (authRepository.signOut()) {
                is AuthResponse.Success -> {
                    Log.e("AuthViewModel", "Successfully signed out")
                }

                is AuthResponse.Error -> {
                    Log.e("AuthViewModel", "Error occurred during sign out")
                }
            }
        }
    }

    fun isUserSignedIn(): Boolean {
        return authRepository.isUserSignedIn()
    }

    companion object {
        fun Factory(
            authRepository: AuthRepository
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AuthViewModel(
                    authRepository = authRepository
                )
            }
        }
    }
}


