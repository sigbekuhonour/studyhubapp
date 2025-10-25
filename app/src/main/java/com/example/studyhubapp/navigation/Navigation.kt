package com.example.studyhubapp.navigation

import android.widget.Toast
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyhubapp.data.datasource.remote.RemoteStorageDataSourceProvider
import com.example.studyhubapp.ui.screens.authentication.AuthViewModel
import com.example.studyhubapp.ui.screens.authentication.login.LoginScreen
import com.example.studyhubapp.ui.screens.authentication.sign_up.SignUpScreen
import com.example.studyhubapp.ui.screens.flashcards.FlashCardListDetailScreen
import com.example.studyhubapp.ui.screens.flashcards.FlashcardViewModel
import com.example.studyhubapp.ui.screens.note.NoteEditorScreen
import com.example.studyhubapp.ui.screens.note.NoteListDetailScreen
import com.example.studyhubapp.ui.screens.note.NoteViewModel
import com.example.studyhubapp.ui.screens.notefolder.NoteFolderDetailScreen
import com.example.studyhubapp.ui.screens.notefolder.NoteFolderViewModel
import com.example.studyhubapp.ui.screens.notefolder.RenameFolderScreen


@Composable
fun AppNav(modifier: Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val dataSource = RemoteStorageDataSourceProvider.getInstance(context)
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)

    NavHost(
        navController = navController,
        startDestination = if (authViewModel.isUserSignedIn()) "LandingPage" else "signup",
        enterTransition = { fadeIn() + slideInHorizontally() },
        exitTransition = { fadeOut() + slideOutHorizontally() }) {

        composable(route = "signup") {
            SignUpScreen(
                viewModel = authViewModel,
                navController = navController,
            )
        }
        composable(route = "login") {
            LoginScreen(
                viewModel = authViewModel,
                navController = navController,
            )
        }
        composable(route = "LandingPage") {
            val noteFolderViewModel: NoteFolderViewModel =
                viewModel(factory = NoteFolderViewModel.Factory(dataSource))
            NoteFolderDetailScreen(
                noteFolderViewModel = noteFolderViewModel,
                navController = navController,
                onSignOut = {
                    authViewModel.signOut()
                    navController.navigate("signup") {
                        popUpTo(0) { inclusive = true }
                    }
                    Toast.makeText(context, "Sign out successful", Toast.LENGTH_SHORT).show()
                }
            )
        }
        composable(
            route = "NoteList/{folderName}/{folderId}",
            enterTransition = { slideInVertically() },
        ) { navBackStackEntry ->
            val viewModel: NoteViewModel = viewModel(factory = NoteViewModel.Factory(dataSource))
            val folderName = navBackStackEntry.arguments?.getString("folderName")
            val folderId = navBackStackEntry.arguments?.getString("folderId")?.toInt()
            if (folderName != null) {
                NoteListDetailScreen(
                    folderName = folderName,
                    folderId = folderId,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
        composable(
            route = "rename/{folderId}/{folderName}",
            enterTransition = { scaleIn() }) { navBackStackEntry ->
            val noteFolderViewModel: NoteFolderViewModel =
                viewModel(factory = NoteFolderViewModel.Factory(dataSource))
            val folderName = navBackStackEntry.arguments?.getString("folderName")
            val folderId = navBackStackEntry.arguments?.getString("folderId")?.toInt()
            if (folderName != null && folderId != null) {
                RenameFolderScreen(
                    folderId = folderId,
                    currentFolderName = folderName,
                    navController = navController,
                    viewModel = noteFolderViewModel
                )
            }

        }
        composable(route = "Note/{folderName}/{folderId}/{title}") { navBackStackEntry ->
            val viewModel: NoteViewModel = viewModel(factory = NoteViewModel.Factory(dataSource))
            val folderName = requireNotNull(navBackStackEntry.arguments?.getString("folderName"))
            val folderId =
                requireNotNull(navBackStackEntry.arguments?.getString("folderId")?.toInt())
            val title = requireNotNull(navBackStackEntry.arguments?.getString("title"))
            LaunchedEffect(folderId, title) {
                if (viewModel.getNoteId(folderId, title) == null) {
                    viewModel.addNotesToFolderWithId(folderId, title)
                }
            }
            NoteEditorScreen(
                folderName = folderName,
                folderId = folderId,
                viewModel = viewModel,
                navController = navController,
                title = title
            )
        }
        composable(route = "flashcards") {
            val viewModel: FlashcardViewModel =
                viewModel(factory = FlashcardViewModel.Factory(dataSource))
            FlashCardListDetailScreen(navController = navController, viewModel = viewModel)
        }
    }
}


