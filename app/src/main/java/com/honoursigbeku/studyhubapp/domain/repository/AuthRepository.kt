package com.honoursigbeku.studyhubapp.domain.repository

import android.content.Context
import com.honoursigbeku.studyhubapp.ui.screens.authentication.AuthResponse
import com.honoursigbeku.studyhubapp.ui.screens.authentication.AuthState
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    fun authState(): StateFlow<AuthState>
    suspend fun signInWithGoogle(
        context: Context,
        serverClientId: String,
    ): AuthResponse

    suspend fun signInWithEmail(
        email: String,
        password: String
    ): AuthResponse

    fun getCurrentUserId(): String?
    fun isUserSignedIn(): Boolean
    suspend fun signUpWithEmail(
        email: String,
        password: String
    ): AuthResponse

    suspend fun signOut(): AuthResponse

}
