package com.example.employeedata.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students_table")
data class EmployeeRecord(
    @PrimaryKey(autoGenerate = true)
    val employeeId: Int,

    val name: String,
    val age: Int,
    val dob: Long,
    val gender: String,
    val mobileNo: String
)