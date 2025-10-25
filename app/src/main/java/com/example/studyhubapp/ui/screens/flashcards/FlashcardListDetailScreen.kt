package com.example.studyhubapp.ui.screens.flashcards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashCardListDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: FlashcardViewModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My flashcards",
                        color = MaterialTheme.colorScheme.scrim,
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                        modifier = Modifier.clickable { navController.popBackStack() },
                        contentDescription = null
                    )
                },
            )
        }, containerColor = MaterialTheme.colorScheme.onPrimary
    ) { paddingValues ->
        val pagerState = rememberPagerState(pageCount = { 10 })
        Column(modifier = Modifier.padding(paddingValues)) {
            HorizontalPager(state = pagerState) {
                FlashcardItem()
            }
        }
    }
}

