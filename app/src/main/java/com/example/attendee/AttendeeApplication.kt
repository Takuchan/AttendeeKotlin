package com.example.attendee

import android.app.Application
import com.example.attendee.database.AttendeeDatabase
import com.example.attendee.database.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class AttendeeApplication:Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AttendeeDatabase.getDatabase(this,applicationScope)}
    val repository by lazy { ProfileRepository(database.userDao())}
}