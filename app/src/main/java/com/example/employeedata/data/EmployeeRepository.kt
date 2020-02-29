package com.example.employeedata.data

import androidx.lifecycle.MutableLiveData
import com.example.employeedata.database.EmployeeDao
import com.example.employeedata.database.EmployeeRecord

class EmployeeRepository(private val employeeDao: EmployeeDao) {

    fun getAllRecords() = employeeDao.getAll()

    fun insert(vararg employee: EmployeeRecord) {
        employeeDao.insertAll(*employee)
    }

    fun update(employee: EmployeeRecord) {
        employeeDao.update(employee)
    }

    fun delete(employee: EmployeeRecord) {
        employeeDao.delete(employee)
    }
}