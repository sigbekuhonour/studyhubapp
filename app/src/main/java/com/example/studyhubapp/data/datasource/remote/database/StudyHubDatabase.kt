package com.example.studyhubapp.data.datasource.remote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.studyhubapp.data.datasource.remote.dao.FolderDao
import com.example.studyhubapp.data.datasource.remote.dao.NoteDao
import com.example.studyhubapp.data.datasource.remote.entities.FolderEntity
import com.example.studyhubapp.data.datasource.remote.entities.NoteEntity

@Database(
    entities = [FolderEntity::class, NoteEntity::class],
    version = 3
)
abstract class StudyHubDatabase : RoomDatabase() {
    abstract fun folderDao(): FolderDao
    abstract fun noteDao(): NoteDao

    companion object {
        const val NAME = "StudyHub_DB"

        @Volatile
        private var INSTANCE: StudyHubDatabase? = null

        fun getDatabase(context: Context): StudyHubDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudyHubDatabase::class.java,
                    NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
