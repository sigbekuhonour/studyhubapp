package com.example.studyhubapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyhubapp.appUI.MainPageUI
import com.example.studyhubapp.viewModel.AppViewModel

@Composable
fun AppNav(viewModel: AppViewModel) {
    ///nav controller
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LandingPage") {
        composable("LandingPage") { MainPageUI(viewModel = viewModel) }
    }
}
