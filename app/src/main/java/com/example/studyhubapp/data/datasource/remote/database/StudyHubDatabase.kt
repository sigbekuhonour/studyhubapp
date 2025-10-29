package com.example.studyhubapp.data.datasource.remote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.studyhubapp.data.datasource.remote.dao.FlashcardDao
import com.example.studyhubapp.data.datasource.remote.dao.FolderDao
import com.example.studyhubapp.data.datasource.remote.dao.NoteDao
import com.example.studyhubapp.data.datasource.remote.entities.FlashcardEntity
import com.example.studyhubapp.data.datasource.remote.entities.FolderEntity
import com.example.studyhubapp.data.datasource.remote.entities.NoteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Database(
    entities = [FolderEntity::class, NoteEntity::class, FlashcardEntity::class],
    version = 1
)
abstract class StudyHubDatabase : RoomDatabase() {
    abstract fun folderDao(): FolderDao
    abstract fun noteDao(): NoteDao
    abstract fun flashcardDao(): FlashcardDao

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
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                                val database = getDatabase(context)
                                database.folderDao().apply {
                                    addFolder(FolderEntity(title = "Quick Notes"))
                                    addFolder(FolderEntity(title = "Shared Notes"))
                                    addFolder(FolderEntity(title = "Deleted Notes"))
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
