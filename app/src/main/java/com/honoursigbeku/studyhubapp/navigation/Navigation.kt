package com.honoursigbeku.studyhubapp.navigation

import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.honoursigbeku.studyhubapp.data.datasource.local.LocalStorageDataSourceProvider
import com.honoursigbeku.studyhubapp.data.datasource.remote.RemoteStorageDataSourceProvider
import com.honoursigbeku.studyhubapp.data.repository.AuthRepositoryImpl
import com.honoursigbeku.studyhubapp.data.repository.FlashcardRepositoryImpl
import com.honoursigbeku.studyhubapp.data.repository.NoteFolderRepositoryImpl
import com.honoursigbeku.studyhubapp.data.repository.NoteRepositoryImpl
import com.honoursigbeku.studyhubapp.ui.screens.authentication.AuthState
import com.honoursigbeku.studyhubapp.ui.screens.authentication.AuthViewModel
import com.honoursigbeku.studyhubapp.ui.screens.authentication.login.LoginScreen
import com.honoursigbeku.studyhubapp.ui.screens.authentication.sign_up.SignUpScreen
import com.honoursigbeku.studyhubapp.ui.screens.flashcards.FlashCardListDetailScreen
import com.honoursigbeku.studyhubapp.ui.screens.flashcards.FlashcardViewModel
import com.honoursigbeku.studyhubapp.ui.screens.note.NoteEditorScreen
import com.honoursigbeku.studyhubapp.ui.screens.note.NoteListDetailScreen
import com.honoursigbeku.studyhubapp.ui.screens.note.NoteViewModel
import com.honoursigbeku.studyhubapp.ui.screens.notefolder.NoteFolderDetailScreen
import com.honoursigbeku.studyhubapp.ui.screens.notefolder.NoteFolderViewModel
import com.honoursigbeku.studyhubapp.ui.screens.notefolder.RenameFolderScreen


@OptIn(UnstableApi::class)
@Composable
fun AppNav(modifier: Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val localDataSource = LocalStorageDataSourceProvider.getInstance(context)
    val remoteDataSource = RemoteStorageDataSourceProvider.getInstance

    val authRepository = remember {
        AuthRepositoryImpl(localDataSource, remoteDataSource)
    }
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.Factory(
            authRepository = authRepository
        )
    )
    val folderRepository = remember {
        NoteFolderRepositoryImpl(localDataSource, remoteDataSource)
    }
    val noteRepository = remember {
        NoteRepositoryImpl(localDataSource, remoteDataSource)
    }
    val flashcardRepository = remember {
        FlashcardRepositoryImpl(
            localDataSource = localDataSource,
            remoteDataSource = remoteDataSource
        )
    }
    val authState by authRepository.authState().collectAsState()
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                Toast.makeText(
                    context, "Authentication successful", Toast.LENGTH_SHORT
                ).show()
                navController.navigate("LandingPage")
            }

            is AuthState.Loading -> {
                Toast.makeText(context, "Verifying credentials", Toast.LENGTH_SHORT).show()
            }

            is AuthState.Unauthenticated -> {}
            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = if (authViewModel.isUserSignedIn()) "landingPage" else "signupPage",
        enterTransition = { fadeIn() + slideInHorizontally() },
        exitTransition = { fadeOut() + slideOutHorizontally() }) {

        composable(route = "signupPage") {
            SignUpScreen(
                viewModel = authViewModel,
                navController = navController,
            )
        }
        composable(route = "loginPage") {
            LoginScreen(
                viewModel = authViewModel,
                navController = navController,
            )
        }
        composable(route = "landingPage") {
            val noteFolderViewModel: NoteFolderViewModel = viewModel(
                factory = NoteFolderViewModel.Factory(
                    authRepository = authRepository,
                    folderRepository = folderRepository
                )
            )
            NoteFolderDetailScreen(
                noteFolderViewModel = noteFolderViewModel,
                navController = navController,
                onSignOut = {
                    authViewModel.signOut()
                    Toast.makeText(context, "Sign out successful", Toast.LENGTH_SHORT).show()
                },
                onDeleteAccount = {
                    authViewModel.deleteUserAccount()
                    Toast.makeText(context, "Account successfully deleted", Toast.LENGTH_SHORT)
                        .show()
                })
        }
        composable(
            route = "noteListPage/{deletedNotesFolderId}/{folderName}/{folderId}",
            enterTransition = { slideInVertically() },
        ) { navBackStackEntry ->
            val noteViewModel: NoteViewModel = viewModel(
                factory = NoteViewModel.Factory(
                    noteRepository = noteRepository
                )
            )
            val folderName = navBackStackEntry.arguments?.getString("folderName")
            val folderId = navBackStackEntry.arguments?.getString("folderId")
            val deletedNotesFolderId =
                navBackStackEntry.arguments?.getString("deletedNotesFolderId")
            if (folderName != null) {
                deletedNotesFolderId?.let {
                    NoteListDetailScreen(
                        folderName = folderName,
                        deletedNotesFolderId = it,
                        folderId = folderId,
                        viewModel = noteViewModel,
                        navController = navController
                    )
                }
            }
        }
        composable(
            route = "renameFolderPage/{folderId}/{folderName}",
            enterTransition = { scaleIn() }) { navBackStackEntry ->
            val noteFolderViewModel: NoteFolderViewModel = viewModel(
                factory = NoteFolderViewModel.Factory(
                    authRepository = authRepository,
                    folderRepository = folderRepository
                )
            )
            val folderName = navBackStackEntry.arguments?.getString("folderName")
            val folderId = navBackStackEntry.arguments?.getString("folderId")
            if (folderName != null && folderId != null) {
                RenameFolderScreen(
                    folderId = folderId,
                    currentFolderName = folderName,
                    navController = navController,
                    viewModel = noteFolderViewModel
                )
            }

        }
        composable(route = "notePage/{folderName}/{folderId}/{title}") { navBackStackEntry ->
            val noteViewModel: NoteViewModel = viewModel(
                factory = NoteViewModel.Factory(
                    noteRepository = noteRepository
                )
            )
            val folderName = requireNotNull(navBackStackEntry.arguments?.getString("folderName"))
            val folderId =
                requireNotNull(navBackStackEntry.arguments?.getString("folderId"))
            val title = requireNotNull(navBackStackEntry.arguments?.getString("title"))
            LaunchedEffect(Unit) {
                noteViewModel.initializeNote(folderId, title)
            }
            NoteEditorScreen(
                folderName = folderName,
                folderId = folderId,
                viewModel = noteViewModel,
                navController = navController,
                title = title
            )
        }
        composable(route = "flashcardPage/{noteId}") { navBackStackEntry ->
            val viewModel: FlashcardViewModel = viewModel(
                factory = FlashcardViewModel.Factory(
                    flashcardRepository = flashcardRepository,
                )
            )
            val noteId = requireNotNull(navBackStackEntry.arguments?.getString("noteId"))
            FlashCardListDetailScreen(
                noteId = noteId, viewModel = viewModel, navController = navController
            )
        }
    }
}


