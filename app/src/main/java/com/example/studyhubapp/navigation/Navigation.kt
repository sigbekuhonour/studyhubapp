package com.example.studyhubapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyhubapp.ui.note.NoteListDetailScreen
import com.example.studyhubapp.ui.note.NoteViewModel
import com.example.studyhubapp.ui.notefolder.NoteFolderDetailScreen
import com.example.studyhubapp.ui.notefolder.NoteFolderViewModel


@Composable
fun AppNav() {
    ///nav controller
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LandingPage") {
        composable("LandingPage") {
            val noteFolderViewModel: NoteFolderViewModel =
                viewModel(factory = NoteFolderViewModel.Factory)
            val noteViewModel: NoteViewModel = viewModel(factory = NoteViewModel.Factory)
            NoteFolderDetailScreen(
                noteFolderViewModel = noteFolderViewModel,
                navController = navController
            )
        }
        composable("NoteList/{folderName}/{folderId}") { navBackStackEntry ->
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
    }
}
