package com.example.employeedata.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employeedata.R
import com.example.employeedata.base.BaseFragment
import com.example.employeedata.data.EmployeeViewModel
import com.example.employeedata.database.EmployeeRecord
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import kotlinx.android.synthetic.main.fragment_employee_list.*

class EmployeeListFragment :BaseFragment() {

    private lateinit var employeeViewModel: EmployeeViewModel
    val viewModel: EmployeeViewModel by lazy { ViewModelProviders.of(this).get(EmployeeViewModel::class.java) }

    private lateinit var employeeAdapter: EmployeeListAdapter

    companion object {
        fun newInstance() = EmployeeListFragment().apply {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_employee_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setTitle("EmployeeData")
        with(recyclerViewStudentList) {
            layoutManager = LinearLayoutManager(context)
        }

        fab.setOnClickListener {
            replace(R.id.fragment_container, EmployeeDetailsFragment.newInstance())
        }
    }

    private fun setObservers() {
        viewModel.getAllRecords().observe(this, Observer {
            println("log get all rows count = ${it.size}")
            setEmployeeList(it)
        })
    }

    private fun setEmployeeList(employeeList: List<EmployeeRecord>) {
        if (employeeList.isEmpty())
            // show error
        else {
            recyclerViewStudentList.adapter = EmployeeListAdapter(context!!, employeeList as MutableList<EmployeeRecord>) { resId, item ->
                if (resId == R.id.imageviewEdit)
                    replace(R.id.fragment_container, EmployeeDetailsFragment.newInstance(item))

                if (resId == R.id.imageviewDelete)
                    println("log click del listener")
            }
        }
    }
}