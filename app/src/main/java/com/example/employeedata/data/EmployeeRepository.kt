package com.example.employeedata.data

import com.example.employeedata.database.EmployeeDao
import com.example.employeedata.database.EmployeeRecord
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class EmployeeRepository(private val employeeDao: EmployeeDao) {

    fun getAllRecords() = employeeDao.getAll()

    fun insert(employee: EmployeeRecord) = employeeDao.insert(employee)

    fun update(employee: EmployeeRecord) = employeeDao.update(employee)

    fun delete(employee: EmployeeRecord) {
        employeeDao.delete(employee)
    }
}