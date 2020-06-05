package com.example.employeedata

import android.app.Application
import com.example.employeedata.data.AppPreferences

class EmployeeApp: Application() {

    override fun onCreate() {
        super.onCreate()

        mInstance = this
        AppPreferences.getInstance(this)
    }

    companion object {
        private lateinit var mInstance: Application
        fun getAppContext() = mInstance
    }
}