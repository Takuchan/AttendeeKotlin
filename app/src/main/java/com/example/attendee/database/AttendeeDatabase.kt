package com.example.attendee.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(AttendeeEntity::class,ProfileEntity::class), version = 1, exportSchema = false)
abstract class AttendeeDatabase : RoomDatabase() {
    abstract fun userDao(): ProfileDao
    abstract fun attendeeDao(): AttendeeDao
    companion object {
        @Volatile
        private var INSTANCE: AttendeeDatabase? = null
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AttendeeDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AttendeeDatabase::class.java,
                    "ATTENDEE.db"
                )

                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }



        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
//                        populateDatabase(database.wordDao())
                    }
                }
            }
        }
    }
}