package com.example.employeedata

import android.app.Application
import com.example.employeedata.data.AppPreferences

class EmployeeApp: Application() {

    override fun onCreate() {
        super.onCreate()

        AppPreferences.getInstance(this)
    }
}