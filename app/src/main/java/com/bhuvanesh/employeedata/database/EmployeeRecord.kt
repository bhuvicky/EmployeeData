package com.bhuvanesh.employeedata.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

// TODO: New Learn; kotlin way to impl Parcelable...
@Parcelize
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
) : Parcelable