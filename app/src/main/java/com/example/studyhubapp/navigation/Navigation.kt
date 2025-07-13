package com.example.studyhubapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyhubapp.ui.NoteFolderScreen
import com.example.studyhubapp.ui.notefolder.NoteFolderViewModel


@Composable
fun AppNav(modifier: Modifier, viewModel: NoteFolderViewModel) {
    ///nav controller
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LandingPage") {
        composable("LandingPage") {
            val viewModel: NoteFolderViewModel = viewModel()
            NoteFolderScreen(viewModel = viewModel)
        }//
    }
}
