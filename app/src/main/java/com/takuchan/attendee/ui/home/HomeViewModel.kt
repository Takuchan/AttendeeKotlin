package com.takuchan.attendee.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takuchan.attendee.database.ProfileEntity

class HomeViewModel : ViewModel() {

    private val _profileData = MutableLiveData<List<ProfileEntity>>().apply {
        value = listOf()
    }

    val profileData: LiveData<List<ProfileEntity>> = _profileData
}