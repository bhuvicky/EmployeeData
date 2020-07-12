package com.bhuvanesh.employeedata.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EmployeeDao {

    //  You can also make your queries observable using LiveData
    // All observable queries by default run on worker thread, by Room
    @Query("SELECT * FROM students_table")
    fun getAll(): LiveData<List<EmployeeRecord>>

    // A method, annotated with @Insert can return a long. This is the newly generated ID for the inserted row
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(employee: EmployeeRecord) : Long

    // A method, annotated with @Update can return an int. This is the number of updated rows.
    @Update
    fun update(employee: EmployeeRecord) : Int

    @Delete
    fun delete(employee: EmployeeRecord)
}