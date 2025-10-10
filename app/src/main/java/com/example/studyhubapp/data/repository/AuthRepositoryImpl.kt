package com.example.studyhubapp.data.repository

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.studyhubapp.domain.repository.AuthRepository
import com.example.studyhubapp.ui.screens.authentication.AuthResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.security.MessageDigest
import java.util.UUID


class AuthRepositoryImpl : AuthRepository {
    private val auth = Firebase.auth


    override suspend fun signInWithGoogle(
        context: Context,
        serverClientId: String,
    ): Flow<AuthResponse> =
        callbackFlow {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false).setServerClientId(serverClientId)
                .setAutoSelectEnabled(false)
                .setNonce(createNonce())
                .build()
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()
            try {
                val credentialManager = CredentialManager.create(context)
                val result = credentialManager.getCredential(context = context, request = request)
                val credential = result.credential
                if (credential is CustomCredential) {
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        try {
                            val googleIdTokenCredential =
                                GoogleIdTokenCredential.createFrom(credential.data)
                            val firebaseCredential =
                                GoogleAuthProvider.getCredential(
                                    googleIdTokenCredential.idToken,
                                    null
                                )

                            auth.signInWithCredential(firebaseCredential).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    trySend(AuthResponse.Success)
                                } else {
                                    trySend(
                                        AuthResponse.Error(
                                            message = it.exception?.message
                                                ?: "An error occurred while attempting to create a new account using this Google account."
                                        )
                                    )
                                }
                            }
                        } catch (e: GoogleIdTokenParsingException) {
                            trySend(AuthResponse.Error(message = e.message ?: "Unknown error"))
                        }

                    }
                }
            } catch (e: Exception) {
                trySend(AuthResponse.Error(message = e.message ?: ""))
            }
            awaitClose()
        }

    override suspend fun signUpWithEmail(
        email: String,
        password: String
    ): Flow<AuthResponse> = callbackFlow {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                trySend(AuthResponse.Success)
            } else {
                trySend(AuthResponse.Error(message = task.exception?.message ?: "Unknown error"))
            }
        }
        awaitClose()
    }

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): Flow<AuthResponse> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                trySend(AuthResponse.Success)
            } else {
                trySend(
                    AuthResponse.Error(
                        message = task.exception?.message ?: "Unknown error"
                    )
                )
            }
        }
        awaitClose()
    }

    override fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun signOut(): AuthResponse {
        return try {
            auth.signOut()
            AuthResponse.Success
        } catch (e: Exception) {
            AuthResponse.Error("Sign out failed: ${e.message}")
        }
    }

    private fun createNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

}
