package com.takuchan.attendee.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profileentity")
    fun getAll(): Flow<List<ProfileEntity>>

    @Query("SELECT * FROM profileentity WHERE id = 1;")
    fun getMyProfile(): LiveData<ProfileEntity>

    @Update
    fun updateAll(profileEntity: ProfileEntity)


    @Insert
    fun insertAll(profileEntity: ProfileEntity)

    @Delete
    fun delete(profileEntity: ProfileEntity)
}