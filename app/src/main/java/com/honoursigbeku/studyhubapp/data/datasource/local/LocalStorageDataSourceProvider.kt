package com.honoursigbeku.studyhubapp.data.datasource.local

import android.content.Context
import com.honoursigbeku.studyhubapp.data.datasource.local.database.StudyHubLocalDatabase


object LocalStorageDataSourceProvider {
    fun getInstance(context: Context): LocalStorageDataSourceImpl {
        val db = StudyHubLocalDatabase.getDatabase(context)
        return LocalStorageDataSourceImpl(
            folderDao = db.folderDao(),
            noteDao = db.noteDao(),
            flashcardDao = db.flashcardDao()
        )
    }
}