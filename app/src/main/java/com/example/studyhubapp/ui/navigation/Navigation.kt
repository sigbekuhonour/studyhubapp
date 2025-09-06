package com.example.studyhubapp.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyhubapp.ui.screens.authentication.AuthViewModel
import com.example.studyhubapp.ui.screens.authentication.login.LoginScreen
import com.example.studyhubapp.ui.screens.authentication.sign_up.SignUpScreen
import com.example.studyhubapp.ui.screens.note.NoteEditorScreen
import com.example.studyhubapp.ui.screens.note.NoteListDetailScreen
import com.example.studyhubapp.ui.screens.note.NoteViewModel
import com.example.studyhubapp.ui.screens.notefolder.NoteFolderDetailScreen
import com.example.studyhubapp.ui.screens.notefolder.NoteFolderViewModel
import com.example.studyhubapp.ui.screens.notefolder.RenameFolderScreen


@Composable
fun AppNav(modifier: Modifier) {
    ///nav controller
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "sign_up",
        enterTransition = { fadeIn() + slideInHorizontally() },
        exitTransition = { fadeOut() + slideOutHorizontally() }) {
        composable(route = "sign_up") {
            val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
            SignUpScreen(
                viewModel = authViewModel,
                navController = navController
            )
        }
        composable(route = "login") {
            val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
            LoginScreen(
                viewModel = authViewModel,
                navController = navController
            )
        }
        composable(route = "LandingPage") {
            val noteFolderViewModel: NoteFolderViewModel =
                viewModel(factory = NoteFolderViewModel.Factory)
            NoteFolderDetailScreen(
                noteFolderViewModel = noteFolderViewModel,
                navController = navController
            )
        }
        composable(
            route = "NoteList/{folderName}/{folderId}",
            enterTransition = { slideInVertically() },
        ) { navBackStackEntry ->
            val viewModel: NoteViewModel = viewModel(factory = NoteViewModel.Factory)
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
                viewModel(factory = NoteFolderViewModel.Factory)
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
            val viewModel: NoteViewModel = viewModel(factory = NoteViewModel.Factory)
            val folderName = navBackStackEntry.arguments?.getString("folderName")
            val folderId = navBackStackEntry.arguments?.getString("folderId")?.toInt()
            val title = navBackStackEntry.arguments?.getString("title")
            if (folderName != null && folderId != null && title != null) {
                NoteEditorScreen(
                    folderName = folderName,
                    folderId = folderId,
                    viewModel = viewModel,
                    navController = navController,
                    title = title
                )
            }

        }
    }
}
