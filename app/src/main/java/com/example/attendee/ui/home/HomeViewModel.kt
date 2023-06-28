package com.example.attendee.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.attendee.database.ProfileEntity

class HomeViewModel : ViewModel() {

    private val _profileData = MutableLiveData<List<ProfileEntity>>().apply {
        value = listOf()
    }

    val profileData: LiveData<List<ProfileEntity>> = _profileData
}