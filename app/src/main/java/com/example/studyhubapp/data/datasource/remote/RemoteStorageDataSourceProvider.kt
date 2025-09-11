package com.example.studyhubapp.data.datasource.remote

object RemoteStorageDataSourceProvider {
    val instance = RemoteStorageDataSourceImpl(
        folderDao = TODO(),
        noteDao = TODO()
    )
}