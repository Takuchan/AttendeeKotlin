package com.takuchan.attendee

import android.widget.EditText

interface ValidationTools {
    fun isEmptyEditText(editText: EditText?): Boolean
    fun changeEditTextToString(editText: EditText?): String?
}