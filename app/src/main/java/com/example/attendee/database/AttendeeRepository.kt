package com.example.attendee.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class AttendeeRepository(private val attendeeDao: AttendeeDao) {
    val allAttendee: Flow<List<AttendeeEntity>> = attendeeDao.getAll()

    @Suppress
    @WorkerThread
    suspend fun insert(profileEntity: AttendeeEntity){
        attendeeDao.insertAll(profileEntity)
    }
    suspend fun updateProfile(profileEntity: AttendeeEntity){
        attendeeDao.updateAll(profileEntity)
    }


}