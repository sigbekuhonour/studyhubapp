package com.honoursigbeku.studyhubapp.data.datasource.remote

import com.honoursigbeku.studyhubapp.R
import com.honoursigbeku.studyhubapp.data.datasource.remote.mapper.toDomain
import com.honoursigbeku.studyhubapp.data.datasource.remote.mapper.toDto
import com.honoursigbeku.studyhubapp.domain.model.Flashcard
import com.honoursigbeku.studyhubapp.domain.model.Folder
import com.honoursigbeku.studyhubapp.domain.model.Note
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteStorageDataSourceImpl : RemoteDataSource {
    val supabase = createSupabaseClient(
        supabaseUrl = R.string.project_url.toString(), supabaseKey = R.string.anon_key.toString()
    ) {
        install(Postgrest)
    }

    override suspend fun getAllFolders(): List<Folder> = withContext(Dispatchers.IO) {
        val remoteFolderDto = supabase.from("Folder").select()
            .decodeList<com.honoursigbeku.studyhubapp.data.datasource.remote.dto.FolderDto>()
        remoteFolderDto.map { eachFolder ->
            eachFolder.toDomain()
        }
    }


    override suspend fun getAllNotes(): List<Note> = withContext(Dispatchers.IO) {
        val remoteNoteDto = supabase.from("Note").select()
            .decodeList<com.honoursigbeku.studyhubapp.data.datasource.remote.dto.NoteDto>()
        remoteNoteDto.map { eachNote ->
            eachNote.toDomain()
        }
    }

    override suspend fun getAllFlashcards(): List<Flashcard> = withContext(Dispatchers.IO) {
        val remoteFlashcardDto = supabase.from("Flashcard").select()
            .decodeList<com.honoursigbeku.studyhubapp.data.datasource.remote.dto.FlashcardDto>()
        remoteFlashcardDto.map { remoteFlashcard ->
            Flashcard(
                id = remoteFlashcard.id,
                ownerNoteId = remoteFlashcard.ownerNoteId,
                content = remoteFlashcard.content
            )
        }
    }

    override suspend fun deleteFolderById(folderId: Int) {
        withContext(Dispatchers.IO) {
            try {
                supabase.from("Folder").delete {
                    filter {
                        eq("id", folderId)
                    }
                }
            } catch (e: Exception) {
                println("Unable to delete folder with id: $folderId from supabase /n $e")
            }
        }
    }

    override suspend fun deleteFlashcardById(flashcardId: Int, noteId: Int) {
        withContext(Dispatchers.IO) {
            try {
                supabase.from("Flashcard").delete {
                    filter {
                        eq("id", flashcardId)
                        eq("ownerNoteId", noteId)
                    }
                }
            } catch (e: Exception) {
                println("Unable to delete folder with id: $flashcardId from supabase /n $e")
            }
        }
    }

    override suspend fun deleteNoteById(folderId: Int, noteId: Int) {
        withContext(Dispatchers.IO) {
            try {
                supabase.from("Note").delete {
                    filter {
                        eq("id", noteId)
                        eq("ownerFolderId", folderId)
                    }
                }
            } catch (e: Exception) {
                println("Unable to delete note with id: $noteId from supabase /n $e")
            }
        }
    }

    override suspend fun addFolder(folder: Folder) {
        withContext(Dispatchers.IO) {
            try {

                supabase.from("Folder").insert(folder.toDto())
            } catch (e: Exception) {
                println("Unable to add folder with id: ${folder.id} into supabase /n $e")
            }
        }
    }

    override suspend fun addFlashcard(flashcard: Flashcard) {
        withContext(Dispatchers.IO) {
            try {

                supabase.from("Flashcard").insert(flashcard.toDto())
            } catch (e: Exception) {
                println("Unable to add flashcard with id: ${flashcard.id} into supabase /n $e")
            }
        }
    }

    override suspend fun addNote(note: Note) {
        withContext(Dispatchers.IO) {
            try {

                supabase.from("Note").insert(note.toDto())
            } catch (e: Exception) {
                println("Unable to add note with id: ${note.id} into supabase /n $e")
            }
        }
    }

    override suspend fun updateFlashcardContent(newContent: String, id: Int) {
        withContext(Dispatchers.IO) {
            try {
                val changes = mapOf("content" to newContent)
                supabase.from("Flashcard").update(changes) {
                    filter {
                        eq("id", id)
                    }
                }
            } catch (e: Exception) {
                println("Unable to update flashcard content with id: $id on supabase /n $e")
            }
        }
    }

    override suspend fun saveNoteChanges(
        folderId: Int, noteId: Int, title: String?, content: String?
    ) {
        withContext(Dispatchers.IO) {
            try {
                val changes = mapOf("content" to content, "title" to title)
                supabase.from("Note").update(changes) {
                    filter {
                        eq("id", noteId)
                        eq("ownerFolderId", folderId)
                    }
                }
            } catch (e: Exception) {
                println("Unable to save note changes with id: $noteId on supabase /n $e")
            }
        }
    }

    override suspend fun updateFolderName(folderId: Int, newFolderName: String) {
        withContext(Dispatchers.IO) {
            try {
                val changes = mapOf("title" to newFolderName)
                supabase.from("Folder").update(changes) {
                    filter {
                        eq("id", folderId)
                    }
                }
            } catch (e: Exception) {
                println("Unable to update folder name with id: $folderId on supabase /n $e")
            }
        }
    }


}
