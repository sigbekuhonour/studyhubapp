package com.honoursigbeku.studyhubapp.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.honoursigbeku.studyhubapp.data.datasource.local.dao.FlashcardDao
import com.honoursigbeku.studyhubapp.data.datasource.local.dao.FolderDao
import com.honoursigbeku.studyhubapp.data.datasource.local.dao.NoteDao
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FlashcardEntity
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.FolderEntity
import com.honoursigbeku.studyhubapp.data.datasource.local.entities.NoteEntity

@Database(
    entities = [FolderEntity::class, NoteEntity::class, FlashcardEntity::class], version = 1
)
abstract class StudyHubLocalDatabase : RoomDatabase() {
    abstract fun folderDao(): FolderDao
    abstract fun noteDao(): NoteDao
    abstract fun flashcardDao(): FlashcardDao

    companion object {
        const val NAME = "StudyHub.db"

        @Volatile
        private var INSTANCE: StudyHubLocalDatabase? = null

        fun getDatabase(context: Context): StudyHubLocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, StudyHubLocalDatabase::class.java, NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
