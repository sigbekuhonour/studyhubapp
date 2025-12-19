package com.honoursigbeku.studyhubapp.ui.screens.flashcards

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun FlashcardItem(
    viewModel: FlashcardViewModel, flashcard: Flashcard, modifier: Modifier = Modifier
) {
    var flipped by rememberSaveable { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (flipped) 180f else 0f, label = "flip")
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .combinedClickable(
                onClick = { flipped = !flipped },
                onDoubleClick = {},
                onLongClick = { expanded = true })
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }, colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inverseSurface)
    ) {

        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (rotation <= 90f) "Click to view" else flashcard.content,
                modifier = Modifier.graphicsLayer {
                    rotationY = if (rotation > 90f) 180f else 0f
                })
            Box(modifier = Modifier.align(Alignment.TopEnd)) {
                DropdownMenu(
                    expanded = expanded,
                    shape = MaterialTheme.shapes.large,
                    onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(text = { Text("Delete flashcard") }, onClick = {
                        viewModel.deleteFlashcard(
                            flashcardId = flashcard.id, noteId = flashcard.noteId
                        )
                        expanded = false
                    })
                }
            }

        }
    }
}