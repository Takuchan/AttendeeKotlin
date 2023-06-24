package com.example.attendee.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profileentity")
    fun getAll(): List<ProfileEntity>

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<ProfileEntity>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): ProfileEntity

    @Update
    fun updateAll(profileEntity: ProfileEntity)
    @Insert
    fun insertAll(profileEntity: ProfileEntity)

    @Delete
    fun delete(profileEntity: ProfileEntity)
}