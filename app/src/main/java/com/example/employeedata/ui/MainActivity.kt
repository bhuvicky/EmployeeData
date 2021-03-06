package com.example.employeedata

import android.os.Bundle
import com.example.employeedata.base.BaseActivity
import com.example.employeedata.ui.EmployeeDetailsFragment
import com.example.employeedata.ui.EmployeeListFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replace(R.id.fragment_container, EmployeeListFragment.newInstance())
    }
}
