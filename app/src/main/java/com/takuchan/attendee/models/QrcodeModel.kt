package com.takuchan.attendee.models

class QrcodeModel(ownerUID: String, sheetId: String) {
    private lateinit var ownerUID: String
    private lateinit var sheetId: String

    init {
        this.ownerUID = ownerUID
        this.sheetId = sheetId
    }
}