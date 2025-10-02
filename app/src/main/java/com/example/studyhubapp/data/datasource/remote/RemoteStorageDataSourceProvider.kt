package com.example.studyhubapp.data.datasource.remote

import android.content.Context
import com.example.studyhubapp.data.datasource.remote.database.StudyHubDatabase

object RemoteStorageDataSourceProvider {
    fun getInstance(context: Context): RemoteStorageDataSourceImpl {
        val db = StudyHubDatabase.getDatabase(context)
        return RemoteStorageDataSourceImpl(
            folderDao = db.folderDao(),
            noteDao = db.noteDao()
        )
    }
}