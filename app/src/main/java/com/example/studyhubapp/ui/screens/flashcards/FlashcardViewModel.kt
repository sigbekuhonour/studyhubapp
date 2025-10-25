package com.example.studyhubapp.ui.screens.flashcards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studyhubapp.data.datasource.DataSource
import com.example.studyhubapp.data.repository.FlashcardRepositoryImpl
import com.example.studyhubapp.domain.repository.FlashcardRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FlashcardViewModel(
    private val flashcardRepository: FlashcardRepository
) : ViewModel() {
    private var _flashcards = flashcardRepository.getAllFlashcards().distinctUntilChanged()

    val flashcards: StateFlow<List<Flashcard>> = _flashcards.map { flashcards ->
        flashcards.map { eachFlashcard ->
            Flashcard(
                id = eachFlashcard.id,
                noteId = eachFlashcard.ownerNoteId,
                content = eachFlashcard.content
            )
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, emptyList())

    fun getFlashcardId(noteId: Int, content: String): Int? {
        return flashcards.value.firstOrNull { eachFlashcard -> eachFlashcard.noteId == noteId && eachFlashcard.content == content }?.id
    }

    fun addFlashcard(content: String, noteId: Int) {
        viewModelScope.launch {
            flashcardRepository.addFlashcard(ownerNoteId = noteId, content = content)
        }
    }

    fun updateFlashcardContent(newContent: String, id: Int) {
        viewModelScope.launch {
            flashcardRepository.updateFlashcardContent(newContent = newContent, id = id)
        }
    }

    fun deleteFlashcard(flashcardId: Int, noteId: Int) {
        viewModelScope.launch {
            flashcardRepository.deleteFlashcardByNoteId(flashcardId = flashcardId, noteId = noteId)
        }
    }

    companion object {
        fun Factory(dataSource: DataSource): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    FlashcardViewModel(
                        flashcardRepository = FlashcardRepositoryImpl(
                            dataSourceImpl = dataSource
                        )
                    )
                }
            }
    }
}