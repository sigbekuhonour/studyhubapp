package com.example.studyhubapp.domain.repository

import android.content.Context
import com.example.studyhubapp.ui.screens.authentication.AuthResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signInWithGoogle(
        context: Context,
        serverClientId: String,
    ): Flow<AuthResponse>

    suspend fun signInWithEmail(
        email: String,
        password: String
    ): Flow<AuthResponse>

    fun userId(): String?
    fun isUserSignedIn(): Boolean
    suspend fun signUpWithEmail(
        email: String,
        password: String
    ): Flow<AuthResponse>

    suspend fun signOut(): AuthResponse

}
