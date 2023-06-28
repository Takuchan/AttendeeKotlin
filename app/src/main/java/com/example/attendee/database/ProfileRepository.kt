package com.example.attendee.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow


class ProfileRepository(private val profileDao: ProfileDao) {
    val allProfile: Flow<List<ProfileEntity>> = profileDao.getAll()
    val myProfile: LiveData<ProfileEntity> = profileDao.getMyProfile()

    @Suppress
    @WorkerThread
    suspend fun insert(profileEntity: ProfileEntity){
        profileDao.insertAll(profileEntity)
    }
    suspend fun updateProfile(profileEntity: ProfileEntity){
        profileDao.updateAll(profileEntity)
    }


}