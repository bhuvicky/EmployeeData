package com.bhuvanesh.employeedata.ui

import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import com.bhuvanesh.employeedata.AppUtil
import com.bhuvanesh.employeedata.EmployeeApp
import com.bhuvanesh.employeedata.R
import com.bhuvanesh.employeedata.base.BaseFragment
import com.bhuvanesh.employeedata.data.AppPreferences
import com.bhuvanesh.employeedata.data.EmployeeViewModel
import com.bhuvanesh.employeedata.database.EmployeeRecord
import com.bhuvanesh.employeedata.extensions.getViewModel
import com.jakewharton.rxbinding3.widget.itemSelections
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import kotlinx.android.synthetic.main.fragment_employee_details.*
import java.util.*


class EmployeeDetailsFragment : BaseFragment() {

    private lateinit var isValid: Observable<Boolean>
    private lateinit var pref: SharedPreferences
    private lateinit var mViewModel: EmployeeViewModel
    private var mEmployeeRecord: EmployeeRecord? = null
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
    private var isFormChanged = false

    companion object {
        fun newInstance(employeeRecord: EmployeeRecord? = null) = EmployeeDetailsFragment().apply {
            mEmployeeRecord = employeeRecord
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_employee_details, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO Doubt: for generic method "getViewModel", we are not mentioned the Type parameter.. how it is working ??
        mViewModel = getViewModel { EmployeeViewModel(EmployeeApp.getAppContext()) }

        mEmployeeRecord = EmployeeDetailsFragmentArgs.fromBundle(requireArguments()).empRec
        // if we use kotlin-android-extensions for "findviewbyid"
        // we can't access UI element id in "onCreateView" method.
        setListeners()
        setObservers()
        pref = AppPreferences.getInstance(requireContext())

        mEmployeeRecord?.let {
            setTitle("Update Employee")
            setUIData(it)
            buttonSave.text = "Update"
        } ?: setTitle("Create New Employee")
    }

    private fun setObservers() {
        var count = 0;
        /*
        * When we subscribe to this (edittext) observable an empty data is thrown which would be considered invalid and the error would pop up.
        * But we don’t want this behavior so skip(1) would do exactly that and skip the first emission.
        * */
        val nameObservable = editTextName.textChanges().skipInitialValue()  // or skip(1)
        val ageObservable = editTextAge.textChanges().skipInitialValue()
        val dobObservable = editTextDob.textChanges().skipInitialValue()
        val genderObservable = spinnerGender.itemSelections()

        // TODO: if we use "combineLatest" from Flowable class, we will get "BackPressureSupport" by default.
        // TODO: Error Faced: "combineLatest" is not useful to update / edit form
        val formDisposable = Observables.combineLatest(
            nameObservable,
            ageObservable,
            dobObservable,
            genderObservable
        ) { name, age, dob, gender ->
            // Once last input field (edittext) starts typing.. comrbineLatest gets trigger...
            // it's not considering the spinner part..
            println("log name = $name")
            println("log age = $age")
            println("log dob = $dob")
            println("log gender = $gender")
            if (++count > 1)
                isFormChanged = true
            isValidForm(name.toString(), age.toString(), dob.toString(), gender)
        }.subscribe {
            buttonSave.isEnabled = it
        }

//        compositeDisposable.addAll(nameObservable, ageObservable, dobObservable, genderObservable, formDisposable)
        mViewModel.insertOperationLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            println("log inserted row id = $it")
            activity?.onBackPressed()
        })

        mViewModel.updateOperationLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            println("log updated rows count = $it")
            activity?.onBackPressed()
        })

        compositeDisposable.add(formDisposable)
    }

    private fun setListeners() {
        val cal = Calendar.getInstance()
        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // "myDob" can only be used in context of AppPreferences
                with(AppPreferences) {
                    pref.myDob = cal.timeInMillis
                }
                editTextDob.setText(AppUtil.convertTimeStampToString(cal.timeInMillis), TextView.BufferType.EDITABLE)
            }
        }
//      Add android:focusable="false" in Edittext to allow for a single touch.
        // if we don't add above line, after two touch only date-picker dialog will come..
        editTextDob.setOnClickListener {
            val dialog = DatePickerDialog(
                requireContext(), dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.maxDate = System.currentTimeMillis()
            dialog.show()
        }

        buttonSave.setOnClickListener {
            val employeeRecord = EmployeeRecord(
                employeeId = mEmployeeRecord?.let { it.employeeId } ?: Math.random().toLong(),
                name = editTextName.text.toString(),
                age = editTextAge.text.toString().toInt(),
                dob = with(AppPreferences) { pref.myDob },
                gender = spinnerGender.selectedItemPosition,
                mobileNo = ""
            )
            mEmployeeRecord?.let {
                if (isFormChanged)
                    mViewModel.update(employeeRecord)
            } ?: mViewModel.insert(employeeRecord)
        }
    }

    private fun setUIData(employeeRecord: EmployeeRecord) {
        with(employeeRecord) {
            editTextName.setText(this.name, TextView.BufferType.EDITABLE)
            editTextAge.setText(this.age.toString(), TextView.BufferType.EDITABLE)
            editTextDob.setText(AppUtil.convertTimeStampToString(this.dob), TextView.BufferType.EDITABLE)
            spinnerGender.setSelection(this.gender)
        }
    }


    private fun isValidForm(name: String, age: String, dob: String, gender: Int): Boolean {
        val validName = !name.isEmpty()
        val validAge = !age.isEmpty()
        val validDob = !dob.isEmpty()
        val validGender = gender > 0

        if (!validName)
            editTextName.error = "Please enter valid name"

        if (!validAge)
            editTextAge.error = "Please enter age"

        if (!validDob)
            editTextDob.error = "Please enter dob"

        /*if (!validGender)
            spinnerGender.error = "Please select age"*/
        return validName && validAge && validDob && validGender
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}