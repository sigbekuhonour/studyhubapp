package com.example.studyhubapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studyhubapp.ui.AppViewModel
import com.example.studyhubapp.ui.MainPageUI

@Composable
fun AppNav(modifier: Modifier, viewModel: AppViewModel) {
    ///nav controller
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LandingPage") {
        composable("LandingPage") { MainPageUI(viewModel = viewModel) }
    }
}
