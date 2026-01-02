package com.honoursigbeku.studyhubapp.data.repository

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.honoursigbeku.studyhubapp.data.datasource.local.LocalDataSource
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FolderEntity
import com.honoursigbeku.studyhubapp.data.datasource.remote.RemoteDataSource
import com.honoursigbeku.studyhubapp.domain.model.Folder
import com.honoursigbeku.studyhubapp.domain.model.User
import com.honoursigbeku.studyhubapp.domain.repository.AuthRepository
import com.honoursigbeku.studyhubapp.ui.screens.authentication.AuthResponse
import com.honoursigbeku.studyhubapp.ui.screens.authentication.AuthState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.UUID


class AuthRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : AuthRepository {
    private val auth = Firebase.auth

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    override fun authState(): StateFlow<AuthState> = _authState.asStateFlow()

    init {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            _authState.value = AuthState.Authenticated(currentUser.uid)
        }

        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            _authState.value = if (user != null) {
                AuthState.Authenticated(user.uid)
            } else {
                AuthState.Unauthenticated
            }
        }
    }

    override suspend fun saveUser() {
        auth.currentUser?.let {
            remoteDataSource.addNewUser(
                User(
                    firebaseUserId = it.uid,
                    email = it.email
                )
            )
        }
    }

    override suspend fun signInWithGoogle(
        context: Context,
        serverClientId: String,
    ): Flow<AuthResponse> = callbackFlow {
        val googleIdOption = GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(false)
            .setServerClientId(serverClientId).setAutoSelectEnabled(false).setNonce(createNonce())
            .build()
        val request = GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()
        try {
            _authState.value = AuthState.Loading
            val credentialManager = CredentialManager.create(context)
            val result = credentialManager.getCredential(context = context, request = request)
            val credential = result.credential
            if (credential is CustomCredential) {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val firebaseCredential = GoogleAuthProvider.getCredential(
                            googleIdTokenCredential.idToken, null
                        )

                        auth.signInWithCredential(firebaseCredential).addOnCompleteListener {
                            if (it.isSuccessful) {
                                auth.currentUser?.let { user ->
                                    _authState.value = AuthState.Authenticated(user.uid)
                                    Log.d(
                                        "AuthRepoImpl",
                                        "Successfully change user auth state to 'Authenticated' "
                                    )
                                }
                                trySend(AuthResponse.Success)
                            } else {
                                _authState.value = AuthState.Error(
                                    message = it.exception?.message
                                        ?: "An error occurred while attempting to sign in with google."
                                )
                                trySend(
                                    AuthResponse.Error(
                                        message = it.exception?.message
                                            ?: "An error occurred while attempting to sign in with google."
                                    )
                                )
                            }
                        }
                    } catch (e: GoogleIdTokenParsingException) {
                        _authState.value = AuthState.Error(
                            message = e.message ?: "GoogleIdTokenParsingException error occurred"
                        )
                        trySend(
                            AuthResponse.Error(
                                message = e.message
                                    ?: "GoogleIdTokenParsingException error occurred"
                            )
                        )
                    }

                }
            }
        } catch (e: Exception) {
            _authState.value = AuthState.Error(
                message = e.message ?: "Error occurred during sign in with google. Please try again"
            )
            trySend(
                AuthResponse.Error(
                    message = e.message
                        ?: "An error occurred while trying to sign in with google. Please try again in a bit."
                )
            )
        }
        awaitClose()
    }

    override suspend fun signUpWithEmail(
        email: String, password: String
    ): Flow<AuthResponse> = callbackFlow {
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.currentUser?.let {
                    _authState.value = AuthState.Authenticated(it.uid)
                    Log.d("AuthRepoImpl", "Successfully change user auth state to 'Authenticated' ")
                }
                trySend(AuthResponse.Success)
            } else {
                _authState.value = AuthState.Error(
                    message = task.exception?.message
                        ?: "Error occurred during sign up with email. Please try again"
                )
                trySend(AuthResponse.Error(message = task.exception?.message ?: "Unknown error"))
            }
        }
        awaitClose()
    }

    override suspend fun signInWithEmail(
        email: String, password: String
    ): Flow<AuthResponse> = callbackFlow {
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.currentUser?.let {
                    _authState.value = AuthState.Authenticated(it.uid)
                    Log.d("AuthRepoImpl", "Successfully change user auth state to 'Authenticated' ")
                }
                trySend(AuthResponse.Success)
            } else {
                _authState.value = AuthState.Error(
                    message = task.exception?.message
                        ?: "Error occurred during sign in with email. Please try again"
                )
                trySend(
                    AuthResponse.Error(
                        message = task.exception?.message ?: "Unknown error"
                    )
                )
            }
        }
        awaitClose()
    }

    override fun getCurrentUserId(): String? {
        return (_authState.value as? AuthState.Authenticated)?.userId
    }

    override fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun signOut(): AuthResponse {
        return try {
            auth.signOut()
            _authState.value = AuthState.Unauthenticated
            AuthResponse.Success
        } catch (e: Exception) {
            _authState.value = AuthState.Error(
                message = e.message ?: "Error occurred during sign out. Please try again"
            )
            AuthResponse.Error("Sign out failed: ${e.message}")
        }
    }

    override suspend fun onboardUser() {
        withContext(Dispatchers.IO) {
            val user = auth.currentUser
            user?.let {
                val localCount = localDataSource.getFolderCount(it.uid)
                val remoteCount = remoteDataSource.getFoldersCount(it.uid)
                when (localCount) {
                    0 if remoteCount == 0 -> {
                        val defaultFolders = listOf("Quick Notes", "Shared Notes", "Deleted Notes")

                        val entities = defaultFolders.map { title ->
                            FolderEntity(
                                folderId = UUID.randomUUID().toString(),
                                userId = it.uid,
                                title = title
                            )
                        }

                        localDataSource.insertAllFolders(entities)
                        entities.map { entity ->
                            async {
                                remoteDataSource.addFolder(
                                    Folder(
                                        entity.folderId,
                                        entity.title,
                                        it.uid
                                    )
                                )
                            }
                        }.awaitAll()
                    }

                    0 if remoteCount > 0 -> {
                        val remoteFolders = remoteDataSource.getAllFolders(it.uid)
                        val entities = remoteFolders.map { folder ->
                            FolderEntity(folder.id, folder.userId, folder.title)
                        }
                        localDataSource.insertAllFolders(entities)
                    }
                }
            }
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
