package com.example.attendee.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AttendeeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "location") var location:String?,
    @ColumnInfo(name = "publicmemo") var publicmemo: String?,
    @ColumnInfo(name = "privatememo") var privatememo:String?,
    @ColumnInfo(name="date") var date:String
)