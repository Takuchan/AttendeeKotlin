package com.example.attendee.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "affiliation") val affiliation:String?,
    @ColumnInfo(name = "assignment") val assignment: String?,
    @ColumnInfo(name = "telnumber") val telnumber:String?,
    @ColumnInfo(name="date") val date:String
)