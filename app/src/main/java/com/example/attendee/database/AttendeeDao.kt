package com.example.attendee.database

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

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<ProfileEntity>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): ProfileEntity

    @Update
    fun updateAll(profileEntity: AttendeeEntity)


    @Insert
    fun insertAll(profileEntity: AttendeeEntity)

    @Delete
    fun delete(profileEntity: AttendeeEntity)
}