package com.example.attendee.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProfileEntity::class], version = 1)
abstract class AttendeeDatabase : RoomDatabase() {
    abstract fun userDao(): ProfileDao
    companion object {

        private var INSTANCE: AttendeeDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): AttendeeDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                        AttendeeDatabase::class.java, "ATTENDEE.db")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}