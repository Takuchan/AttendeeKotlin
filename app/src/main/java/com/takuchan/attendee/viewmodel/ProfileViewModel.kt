package com.takuchan.attendee.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.takuchan.attendee.database.ProfileEntity
import com.takuchan.attendee.database.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProfileRepository):ViewModel() {

    val allProfile: LiveData<List<ProfileEntity>> = repository.allProfile.asLiveData()
    val myProfile : LiveData<ProfileEntity> = repository.myProfile

    fun insert(profileEntity: ProfileEntity) = viewModelScope.launch {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(profileEntity)
        }
    }
    fun updateMyProfile(profileEntity: ProfileEntity) = viewModelScope.launch {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProfile(profileEntity)
        }
    }
}
class ProfileViewModelFactory(private val repository: ProfileRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(repository) as T
        }
        throw IllegalAccessException("UNKNOWN VIEWMODEL CLASS")
    }
}