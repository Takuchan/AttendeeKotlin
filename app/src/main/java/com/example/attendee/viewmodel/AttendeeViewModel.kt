package com.example.attendee.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.attendee.database.AttendeeEntity
import com.example.attendee.database.AttendeeRepository
import com.example.attendee.database.ProfileEntity
import com.example.attendee.database.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AttendeeViewModel(private val repository: AttendeeRepository): ViewModel() {

    val allAttendee: LiveData<List<AttendeeEntity>> = repository.allAttendee.asLiveData()

    fun insert(attendeeEntity: AttendeeEntity) = viewModelScope.launch {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(attendeeEntity)
        }
    }
    fun updateMyProfile(attendeeEntity: AttendeeEntity) = viewModelScope.launch {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProfile(attendeeEntity)
        }
    }
}
class AttendeeViewFactory(private val repository: AttendeeRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AttendeeViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return AttendeeViewModel(repository) as T
        }
        throw IllegalAccessException("UNKNOWN VIEWMODEL CLASS")
    }
}