package com.honoursigbeku.studyhubapp.ui.screens.flashcards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.honoursigbeku.studyhubapp.R
import com.honoursigbeku.studyhubapp.ui.component.dialog.SimpleDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashCardListDetailScreen(
    noteId: Int, viewModel: FlashcardViewModel, navController: NavController
) {
    val listOfFlashcards =
        viewModel.flashcards.collectAsState().value.filter { it.noteId == noteId }
    var isNewFlashcardButtonClicked by rememberSaveable { mutableStateOf(false) }
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
                        painter = painterResource(id = R.drawable.arrow_back),
                        tint = MaterialTheme.colorScheme.scrim,
                        modifier = Modifier.clickable { navController.popBackStack() },
                        contentDescription = null
                    )
                },
            )
        }, containerColor = MaterialTheme.colorScheme.onPrimary, floatingActionButton = {
            IconButton(onClick = { isNewFlashcardButtonClicked = !isNewFlashcardButtonClicked }) {
                Icon(
                    painter = painterResource(id = R.drawable.add_circle),
                    tint = MaterialTheme.colorScheme.scrim,
                    contentDescription = null
                )
            }
        }, floatingActionButtonPosition = FabPosition.EndOverlay
    ) { paddingValues ->
        val pagerState = rememberPagerState(pageCount = { listOfFlashcards.size })
        Column(modifier = Modifier.padding(paddingValues)) {
            if (listOfFlashcards.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Oops no flashcard created yet!!",
                        color = MaterialTheme.colorScheme.scrim,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            if (isNewFlashcardButtonClicked) {
                SimpleDialog(
                    dialogHeader = "Create New FlashcardDto",
                    labelText = "Card content",
                    isSingleLine = false,
                    confirmButtonText = "Create New FlashcardDto",
                    onDismiss = { isNewFlashcardButtonClicked = !isNewFlashcardButtonClicked },
                    onConfirm = { newFolderName ->
                        viewModel.addFlashcard(
                            content = newFolderName, noteId = noteId
                        )
                    })
            }
            HorizontalPager(state = pagerState) { page ->
                FlashcardItem(viewModel = viewModel, flashcard = listOfFlashcards[page])
            }
        }
    }
}

