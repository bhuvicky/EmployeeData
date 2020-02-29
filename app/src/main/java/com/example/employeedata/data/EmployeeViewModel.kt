package com.example.employeedata.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.employeedata.database.EmployeeDatabase
import com.example.employeedata.database.EmployeeRecord

class EmployeeViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: EmployeeRepository
    // LiveData gives us updated words when they change.
    var employeeListLiveData: MutableLiveData<List<EmployeeRecord>> = MutableLiveData()

    init {
        val employeeDao = EmployeeDatabase.invoke(application).employeeDao()
        repository = EmployeeRepository(employeeDao)
        employeeListLiveData.value = repository.getAllRecords()
    }
}