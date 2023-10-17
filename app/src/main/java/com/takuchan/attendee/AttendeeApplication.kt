package com.takuchan.attendee

import android.app.Application
import com.takuchan.attendee.database.AttendeeDatabase
import com.takuchan.attendee.database.AttendeeRepository
import com.takuchan.attendee.database.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class AttendeeApplication:Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AttendeeDatabase.getDatabase(this,applicationScope)}
    val repository by lazy { ProfileRepository(database.userDao())}
    val attendeeRepository by lazy { AttendeeRepository(database.attendeeDao())}
}