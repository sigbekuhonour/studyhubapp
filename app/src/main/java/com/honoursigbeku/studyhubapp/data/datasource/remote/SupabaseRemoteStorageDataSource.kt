package com.honoursigbeku.studyhubapp.data.datasource.remote

import android.util.Log
import com.honoursigbeku.studyhubapp.data.datasource.remote.dto.FlashcardDto
import com.honoursigbeku.studyhubapp.data.datasource.remote.dto.FolderDto
import com.honoursigbeku.studyhubapp.data.datasource.remote.dto.NoteDto
import com.honoursigbeku.studyhubapp.data.datasource.remote.mapper.toDomain
import com.honoursigbeku.studyhubapp.data.datasource.remote.mapper.toDto
import com.honoursigbeku.studyhubapp.domain.model.Flashcard
import com.honoursigbeku.studyhubapp.domain.model.Folder
import com.honoursigbeku.studyhubapp.domain.model.Note
import com.honoursigbeku.studyhubapp.domain.model.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SupabaseRemoteStorageDataSourceImpl(private val client: SupabaseClient) : RemoteDataSource {

    override suspend fun addNewUser(user: User) {
        try {
            client.from("User").upsert(
                value = user.toDto()
            ) {
                onConflict = "firebaseUserId"
                ignoreDuplicates = true
            }
            Log.i(
                "SupabaseRemoteDataSource",
                "Successfully added new user to supabase"
            )
        } catch (e: Exception) {
            Log.i(
                "SupabaseRemoteDataSource",
                "Unable to add user with id: ${user.firebaseUserId} to client /n $e"
            )
        }
    }

    override suspend fun getAllFolders(userId: String): List<Folder> = withContext(Dispatchers.IO) {
        val remoteFolderDto = client.from("Folder").select {
            filter {
                eq("userId", userId)
            }
        }.decodeList<FolderDto>()
        Log.i(
            "SupabaseRemoteDataSource",
            "Successfully retrieved all folders belonging to user $userId from supabase"
        )
        remoteFolderDto.map { eachFolder ->
            eachFolder.toDomain()
        }
    }

    override suspend fun getFoldersCount(userId: String): Int {
        val remoteFolderDto = client.from("Folder").select {
            filter {
                eq("userId", userId)
            }
        }.decodeList<FolderDto>()
        Log.i(
            "SupabaseRemoteDataSource",
            "Successfully retrieved all folders belonging to user $userId from supabase with count - ${remoteFolderDto.size}"
        )
        return remoteFolderDto.size
    }


    override suspend fun getAllNotes(): List<Note> = withContext(Dispatchers.IO) {
        val remoteNoteDto = client.from("Note").select().decodeList<NoteDto>()
        Log.i(
            "SupabaseRemoteDataSource",
            "Successfully retrieved all notes from supabase"
        )
        remoteNoteDto.map { eachNote ->
            eachNote.toDomain()
        }
    }

    override suspend fun getAllFlashcards(): List<Flashcard> = withContext(Dispatchers.IO) {
        val remoteFlashcardDto = client.from("Flashcard").select().decodeList<FlashcardDto>()
        Log.i(
            "SupabaseRemoteDataSource",
            "Successfully retrieved all flashcards from supabase"
        )
        remoteFlashcardDto.map { remoteFlashcard ->
            Flashcard(
                id = remoteFlashcard.id,
                ownerNoteId = remoteFlashcard.ownerNoteId,
                content = remoteFlashcard.content
            )
        }
    }

    override suspend fun deleteFolderById(folderId: String) {
        withContext(Dispatchers.IO) {
            try {
                client.from("Folder").delete {
                    filter {
                        eq("id", folderId)
                    }
                }
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Successfully deleted folder from supabase with id $folderId"
                )
            } catch (e: Exception) {
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Unable to delete folder with id: $folderId from client /n $e"
                )
            }
        }
    }

    override suspend fun deleteFlashcardById(flashcardId: String, noteId: String) {
        withContext(Dispatchers.IO) {
            try {
                client.from("Flashcard").delete {
                    filter {
                        eq("id", flashcardId)
                        eq("ownerNoteId", noteId)
                    }
                }
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Successfully deleted flashcard from supabase with id $flashcardId"
                )
            } catch (e: Exception) {
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Unable to delete folder with id: $flashcardId from client /n $e"
                )
            }
        }
    }

    override suspend fun deleteNoteById(folderId: String, noteId: String) {
        withContext(Dispatchers.IO) {
            try {
                client.from("Note").delete {
                    filter {
                        eq("id", noteId)
                        eq("ownerFolderId", folderId)
                    }
                }
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Successfully deleted note from supabase with id $noteId"
                )
            } catch (e: Exception) {
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Unable to delete note with id: $noteId from client /n $e"
                )
            }
        }
    }

    override suspend fun addFolder(folder: Folder) {
        withContext(Dispatchers.IO) {
            try {
                client.from("Folder").insert(folder.toDto())
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Successfully added folder to supabase with id ${folder.id}"
                )
            } catch (e: Exception) {
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Unable to add folder with id: ${folder.id} into client /n $e"
                )
            }
        }
    }

    override suspend fun addFlashcard(flashcard: Flashcard) {
        withContext(Dispatchers.IO) {
            try {
                client.from("Flashcard").insert(flashcard.toDto())
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Successfully added flashcard to supabase with id ${flashcard.id}"
                )
            } catch (e: Exception) {
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Unable to add flashcard with id: ${flashcard.id} into client /n $e"
                )
            }
        }
    }

    override suspend fun addNote(note: Note) {
        withContext(Dispatchers.IO) {
            try {
                client.from("Note").insert(note.toDto())
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Successfully added note to supabase with id ${note.id}"
                )
            } catch (e: Exception) {
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Unable to add note with id: ${note.id} into client /n $e"
                )
            }
        }
    }

    override suspend fun updateFlashcardContent(newContent: String, id: String) {
        withContext(Dispatchers.IO) {
            try {
                val changes = mapOf("content" to newContent)
                client.from("Flashcard").update(changes) {
                    filter {
                        eq("id", id)
                    }
                }
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Successfully updated flashcard content in supabase with id $id"
                )
            } catch (e: Exception) {
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Unable to update flashcard content with id: $id on client /n $e"
                )
            }
        }
    }

    override suspend fun saveNoteChanges(
        folderId: String, noteId: String, title: String?, content: String?
    ) {
        withContext(Dispatchers.IO) {
            try {
                val changes = mapOf("content" to content, "title" to title)
                client.from("Note").update(changes) {
                    filter {
                        eq("id", noteId)
                        eq("ownerFolderId", folderId)
                    }
                }
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Successfully updated note content in supabase with id $noteId"
                )
            } catch (e: Exception) {
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Unable to save note changes with id: $noteId on client /n $e"
                )
            }
        }
    }

    override suspend fun updateFolderName(folderId: String, newFolderName: String) {
        withContext(Dispatchers.IO) {
            try {
                val changes = mapOf("title" to newFolderName)
                client.from("Folder").update(changes) {
                    filter {
                        eq("id", folderId)
                    }
                }
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Successfully updated folder name  in supabase with id $folderId"
                )
            } catch (e: Exception) {
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Unable to update folder name with id: $folderId on client /n $e"
                )
            }
        }
    }

    override suspend fun deleteUserById(userId: String) {
        withContext(Dispatchers.IO) {
            try {
                client.from("User").delete {
                    filter {
                        eq("firebaseUserId", userId)
                    }
                }
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Successfully deleted user from supabase with id $userId"
                )
            } catch (e: Exception) {
                Log.i(
                    "SupabaseRemoteDataSource",
                    "Unable to delete user with id: $userId from client /n $e"
                )
            }
        }
    }


}
