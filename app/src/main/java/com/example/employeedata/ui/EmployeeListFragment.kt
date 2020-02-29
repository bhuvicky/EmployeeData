package com.example.employeedata.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employeedata.base.BaseFragment
import com.example.employeedata.data.EmployeeViewModel
import com.example.employeedata.database.EmployeeRecord
import example.com.employeedata.R
import kotlinx.android.synthetic.main.fragment_employee_list.*

class EmployeeListFragment :BaseFragment() {

    private lateinit var wordViewModel: EmployeeViewModel
    val viewModel: EmployeeViewModel by lazy { ViewModelProviders.of(this).get(EmployeeViewModel::class.java) }

    companion object {
        fun newInstance() = EmployeeListFragment().apply {

        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_employee_list, container, false)
        initViews()
        return view
    }

    private fun initViews() {
        recyclerViewStudentList.layoutManager = LinearLayoutManager(context)
    }

    private fun setObservers() {
        viewModel.employeeListLiveData.observe(this, Observer { setEmployeeList(it) })
    }

    private fun setEmployeeList(employeeList: List<EmployeeRecord>) {

    }
}