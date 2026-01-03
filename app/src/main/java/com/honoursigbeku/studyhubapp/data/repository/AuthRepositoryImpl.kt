package com.honoursigbeku.studyhubapp.data.repository

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.honoursigbeku.studyhubapp.data.datasource.local.LocalDataSource
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FlashcardEntity
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FolderEntity
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.NoteEntity
import com.honoursigbeku.studyhubapp.data.datasource.remote.RemoteDataSource
import com.honoursigbeku.studyhubapp.domain.model.Folder
import com.honoursigbeku.studyhubapp.domain.model.User
import com.honoursigbeku.studyhubapp.domain.repository.AuthRepository
import com.honoursigbeku.studyhubapp.ui.screens.authentication.AuthResponse
import com.honoursigbeku.studyhubapp.ui.screens.authentication.AuthState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.UUID


class AuthRepositoryImpl(
    private val localDataSource: LocalDataSource, private val remoteDataSource: RemoteDataSource
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


    override suspend fun signInWithGoogle(
        context: Context,
        serverClientId: String,
    ): AuthResponse {
        val googleIdOption = GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(false)
            .setServerClientId(serverClientId).setAutoSelectEnabled(false).setNonce(createNonce())
            .build()
        val request = GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()
        val credentialManager = CredentialManager.create(context)
        val result = credentialManager.getCredential(context = context, request = request)
        val credential = result.credential
        _authState.value = AuthState.Loading
        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val firebaseCredential = GoogleAuthProvider.getCredential(
                googleIdTokenCredential.idToken, null
            )
            return try {
                auth.signInWithCredential(firebaseCredential).await()
                auth.currentUser?.let { user ->
                    saveUser()
                    Log.i(
                        "AuthRepoImpl",
                        "Successfully saved user to supabase"
                    )
                    onboardUser()
                    Log.i(
                        "AuthRepoImpl",
                        "Successfully onboarded user with id ${user.uid}"
                    )
                    _authState.value = AuthState.Authenticated(user.uid)
                    Log.i(
                        "AuthRepoImpl",
                        "Successfully changed user auth state to 'Authenticated'"
                    )
                    AuthResponse.Success
                } ?: run {
                    val errorMessage = "User is null after successful sign in"
                    _authState.value = AuthState.Error(message = errorMessage)
                    AuthResponse.Error(message = errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage =
                    e.message ?: "An error occurred while attempting to sign in with Google."
                _authState.value = AuthState.Error(message = errorMessage)
                AuthResponse.Error(message = errorMessage)
            }
        }
        return AuthResponse.Error(message = "You do not have any google account to sign in with currently. Please add a google account and try again!")
    }

    override suspend fun signUpWithEmail(
        email: String, password: String
    ): AuthResponse {
        _authState.value = AuthState.Loading
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            auth.currentUser?.let { user ->
                saveUser()
                Log.i(
                    "AuthRepoImpl",
                    "Successfully saved user to supabase"
                )
                onboardUser()
                Log.i(
                    "AuthRepoImpl",
                    "Successfully onboarded user with id ${user.uid}"
                )
                _authState.value = AuthState.Authenticated(user.uid)
                Log.i(
                    "AuthRepoImpl",
                    "Successfully changed user auth state to 'Authenticated'"
                )
                AuthResponse.Success
            } ?: run {
                val errorMessage = "User is null after successful sign in"
                _authState.value = AuthState.Error(message = errorMessage)
                AuthResponse.Error(message = errorMessage)
            }
        } catch (e: Exception) {
            _authState.value = AuthState.Error(
                message = e.message
                    ?: "Error occurred during sign up with email. Please try again"
            )
            AuthResponse.Error(
                message = e.message ?: "Error occurred during sign up with email. Please try again"
            )
        }
    }

    override suspend fun signInWithEmail(
        email: String, password: String
    ): AuthResponse {
        _authState.value = AuthState.Loading
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            auth.currentUser?.let { user ->
                saveUser()
                Log.i(
                    "AuthRepoImpl",
                    "Successfully saved user to supabase"
                )
                onboardUser()
                Log.i(
                    "AuthRepoImpl",
                    "Successfully onboarded user with id ${user.uid}"
                )
                _authState.value = AuthState.Authenticated(user.uid)
                Log.i(
                    "AuthRepoImpl",
                    "Successfully changed user auth state to 'Authenticated'"
                )
                AuthResponse.Success
            } ?: run {
                val errorMessage = "User is null after successful sign in"
                _authState.value = AuthState.Error(message = errorMessage)
                AuthResponse.Error(message = errorMessage)
            }
        } catch (e: Exception) {
            _authState.value = AuthState.Error(
                message = e.message
                    ?: "Error occurred during sign in with email. Please try again"
            )
            AuthResponse.Error(
                message = e.message ?: "Error occurred during sign in with email. Please try again"
            )
        }
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


    private suspend fun saveUser() {
        auth.currentUser?.let {
            remoteDataSource.addNewUser(
                User(
                    firebaseUserId = it.uid, email = it.email
                )
            )
        }
    }

    private suspend fun onboardUser() {
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
                                        entity.folderId, entity.title, it.uid
                                    )
                                )
                            }
                        }.awaitAll()
                    }

                    0 if remoteCount > 0 -> {
                        val remoteFoldersDeferred = async { remoteDataSource.getAllFolders(it.uid) }
                        val remoteNoteDeferred = async { remoteDataSource.getAllNotes() }
                        val remoteFlashcardDeferred = async { remoteDataSource.getAllFlashcards() }

                        val remoteFolders = remoteFoldersDeferred.await()
                        val remoteNote = remoteNoteDeferred.await()
                        val remoteFlashcard = remoteFlashcardDeferred.await()


                        val folderEntities = remoteFolders.map { folder ->
                            FolderEntity(folder.id, folder.userId, folder.title)
                        }
                        localDataSource.insertAllFolders(folderEntities)

                        if (remoteNote.isNotEmpty()) {
                            val noteEntities = remoteNote.map { note ->
                                NoteEntity(
                                    noteId = note.id,
                                    title = note.title,
                                    content = note.content,
                                    ownerFolderId = note.folderId
                                )
                            }
                            localDataSource.insertAllNotes(noteEntities)
                        }

                        if (remoteFlashcard.isNotEmpty()) {
                            val flashcardEntities = remoteFlashcard.map { flashcard ->
                                FlashcardEntity(
                                    flashcardId = flashcard.id,
                                    ownerNoteId = flashcard.ownerNoteId,
                                    content = flashcard.content
                                )
                            }
                            localDataSource.insertAllFlashcards(flashcardEntities)
                        }
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

