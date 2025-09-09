package com.example.studyhubapp.data.datasource.remote

import com.example.studyhubapp.data.datasource.remote.dao.FolderDao
import com.example.studyhubapp.data.datasource.remote.dao.NoteDao
import kotlinx.coroutines.CoroutineScope

class RemoteStorageDataSourceProvider(
    private val folderDao: FolderDao,
    private val noteDao: NoteDao,
    private val scope: CoroutineScope
) {
    val instance: RemoteStorageDataSourceImpl by lazy {
        RemoteStorageDataSourceImpl(folderDao, noteDao, scope)
    }

    companion object {
        @Volatile
        private var INSTANCE: RemoteStorageDataSourceProvider? = null

        fun getInstance(
            folderDao: FolderDao,
            noteDao: NoteDao,
            scope: CoroutineScope
        ): RemoteStorageDataSourceProvider {
            return INSTANCE ?: synchronized(this) {
                val instance = RemoteStorageDataSourceProvider(folderDao, noteDao, scope)
                INSTANCE = instance
                instance
            }
        }
    }
}