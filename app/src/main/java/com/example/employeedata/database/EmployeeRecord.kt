package com.example.employeedata.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students_table")
data class EmployeeRecord(
    // to make primary key as auto-increment.
    @PrimaryKey(autoGenerate = true)
    val employeeId: Long,

    val name: String,
    val age: Int,
    val dob: Long,
    val gender: Int,
    val mobileNo: String
)