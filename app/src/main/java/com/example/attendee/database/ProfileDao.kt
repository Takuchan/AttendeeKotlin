package com.example.attendee.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

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

    @Insert
    fun insertAll(vararg profileEntity: ProfileEntity)

    @Delete
    fun delete(profileEntity: ProfileEntity)
}