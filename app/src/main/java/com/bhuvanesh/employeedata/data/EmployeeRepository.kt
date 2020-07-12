package com.bhuvanesh.employeedata.data

import com.bhuvanesh.employeedata.database.EmployeeDao
import com.bhuvanesh.employeedata.database.EmployeeRecord


class EmployeeRepository(private val employeeDao: EmployeeDao) {

    fun getAllRecords() = employeeDao.getAll()

    fun insert(employee: EmployeeRecord) = employeeDao.insert(employee)

    fun update(employee: EmployeeRecord) = employeeDao.update(employee)

    fun delete(employee: EmployeeRecord) {
        employeeDao.delete(employee)
    }
}