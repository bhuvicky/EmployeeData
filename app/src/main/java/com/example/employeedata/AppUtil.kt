package com.example.employeedata

import java.text.SimpleDateFormat
import java.util.*

object AppUtil {

    fun convertTimeStampToString(dob: Long) : String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = dob
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(cal.getTime())
    }
}