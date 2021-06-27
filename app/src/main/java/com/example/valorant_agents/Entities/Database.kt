package com.example.roomwordsample.Entities

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.valorant_agents.features.agentsList.Repo.AgentDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(RoomAgent::class, RoomAbility::class, RoomRole::class), version = 3, exportSchema = false)
@TypeConverters(Converters::class)
public abstract class ValorantRoomDatabase : RoomDatabase() {

    abstract fun agentsDao(): AgentDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
        }

    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ValorantRoomDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope): ValorantRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ValorantRoomDatabase::class.java,
                    "word_database"
                ).addCallback(WordDatabaseCallback(scope)).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}

