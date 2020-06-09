package com.example.employeedata.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
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
    private var mEmployeeList: List<EmployeeRecord> = mutableListOf()
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = requireActivity().findNavController(R.id.nav_host_fragment)
        employeeAdapter = EmployeeListAdapter(requireContext()) { resId, item ->
            handleRecyclerViewItemClick(resId, item)
        }
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
            adapter = employeeAdapter
        }
        employeeAdapter.setData(mEmployeeList)

        fab.setOnClickListener {
//            replace(R.id.fragment_container, EmployeeDetailsFragment.newInstance())
            val action = EmployeeListFragmentDirections.actionEmployeeListFragmentToEmployeeDetailsFragment()
            navController.navigate(action)
        }
    }

    private fun setObservers() {
        viewModel.getAllRecords().observe(this, Observer {
            println("log get all rows count = ${it.size}")
            mEmployeeList = it
            setEmployeeList(it)
        })

        viewModel.deleteOperationLiveData.observe(this, Observer {
            employeeAdapter.notifyItemRemoved((mEmployeeList as ArrayList).indexOf(it))
            (mEmployeeList as ArrayList).remove(it)
        })
    }

    private fun setEmployeeList(employeeList: List<EmployeeRecord>) {
        if (employeeList.isEmpty())
            // show error
        else {
            employeeAdapter.setData(employeeList)
        }
    }

    private fun handleRecyclerViewItemClick(resId: Int, item: EmployeeRecord) {
        if (resId == R.id.imageviewEdit) {
            // replace(R.id.fragment_container, EmployeeDetailsFragment.newInstance(item))
            val action = EmployeeListFragmentDirections.actionEmployeeListFragmentToEmployeeDetailsFragment(item)
            navController.navigate(action)
        }

        if (resId == R.id.imageviewDelete)
            showAlertDialog(item)
    }

    private fun showAlertDialog(item: EmployeeRecord) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Item")
        builder.setMessage("Are you sure, you want to delete this item?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            viewModel.delete(item)
            dialog.dismiss()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }
}