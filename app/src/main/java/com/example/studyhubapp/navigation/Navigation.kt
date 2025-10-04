package com.example.studyhubapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyhubapp.data.datasource.local.LocalStorageDataSourceProvider
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
    val dataSource = LocalStorageDataSourceProvider.instance
    NavHost(navController = navController, startDestination = "LandingPage") {
        composable("LandingPage") {
            val noteFolderViewModel: NoteFolderViewModel =
                viewModel(factory = NoteFolderViewModel.Factory(dataSource))
            NoteFolderDetailScreen(
                noteFolderViewModel = noteFolderViewModel,
                navController = navController
            )
        }
        composable("NoteList/{folderName}/{folderId}") { navBackStackEntry ->
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
        composable("rename/{folderId}/{folderName}") { navBackStackEntry ->
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
        composable("Note/{folderName}/{folderId}/{title}") { navBackStackEntry ->
            val viewModel: NoteViewModel = viewModel(factory = NoteViewModel.Factory(dataSource))
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
