package com.example.attendee.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "affiliation") var affiliation:String?,
    @ColumnInfo(name = "assignment") var assignment: String?,
    @ColumnInfo(name = "telnumber") var telnumber:String?,
    @ColumnInfo(name="date") var date:String
)