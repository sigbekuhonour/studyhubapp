package com.example.studyhubapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyhubapp.ui.note.NoteDetail
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
        composable("Note/{folderName}/{folderId}/{title}") { navBackStackEntry ->
            val viewModel: NoteViewModel = viewModel(factory = NoteViewModel.Factory)
            val folderName = navBackStackEntry.arguments?.getString("folderName")
            val folderId = navBackStackEntry.arguments?.getString("folderId")?.toInt()
            val title = navBackStackEntry.arguments?.getString("title")
            if (folderName != null && folderId != null && title != null) {
                NoteDetail(
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
