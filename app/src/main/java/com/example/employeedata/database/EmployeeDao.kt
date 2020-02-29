package com.example.employeedata.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EmployeeDao {

    //  You can also make your queries observable using LiveData
    @Query("SELECT * FROM students_table")
    fun getAll(): List<EmployeeRecord>

    @Insert
    fun insertAll(vararg todo: EmployeeRecord)

    @Delete
    fun delete(todo: EmployeeRecord)

    @Update
    fun update(vararg todos: EmployeeRecord)
}