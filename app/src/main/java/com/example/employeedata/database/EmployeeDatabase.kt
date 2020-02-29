package com.example.employeedata.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class EmployeeDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    companion object {
        @Volatile
        private var instance: EmployeeDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            EmployeeDatabase::class.java, "employee-list.db"
        ).build()
    }
}