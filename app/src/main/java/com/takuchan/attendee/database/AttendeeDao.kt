package com.takuchan.attendee.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendeeDao {
    @Query("SELECT * FROM attendeeentity")
    fun getAll(): Flow<List<AttendeeEntity>>

    @Query("SELECT * FROM AttendeeEntity WHERE id = 1;")
    fun getMyProfile(): LiveData<AttendeeEntity>

    @Query("SELECT * FROM AttendeeEntity WHERE :id")
    fun loadAttendeeData(id: Int): LiveData<AttendeeEntity>

    @Update
    fun updateAll(profileEntity: AttendeeEntity)

    @Insert
    fun insertAll(profileEntity: AttendeeEntity)

    @Delete
    fun delete(profileEntity: AttendeeEntity)
}