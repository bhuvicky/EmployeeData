package com.example.employeedata.ui

import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import com.example.employeedata.R
import com.example.employeedata.base.BaseFragment
import com.example.employeedata.data.AppPreferences
import com.jakewharton.rxbinding3.widget.itemSelections
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import kotlinx.android.synthetic.main.fragment_employee_details.*
import java.text.SimpleDateFormat
import java.util.*


class EmployeeDetailsFragment : BaseFragment() {

    private lateinit var isValid: Observable<Boolean>
    private lateinit var pref: SharedPreferences

    companion object {
        fun newInstance() = EmployeeDetailsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_employee_details, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // if we use kotlin-android-extensions for "findviewbyid"
        // we can't access UI element id in "onCreateView" method.
        setListeners()
        setObservers()
        pref = AppPreferences.getInstance(context!!)

    }

    private fun setObservers() {
        /*
        * When we subscribe to this (edittext) observable an empty data is thrown which would be considered invalid and the error would pop up.
        * But we don’t want this behavior so skip(1) would do exactly that and skip the first emission.
        * */
        val nameObservable = editTextName.textChanges().skipInitialValue()  // or skip(1)
        val ageObservable = editTextAge.textChanges().skipInitialValue()
        val dobObservable = editTextDob.textChanges().skipInitialValue()
        val genderObservable = spinnerGender.itemSelections()

        val formDisposable = Observables.combineLatest(nameObservable, ageObservable, dobObservable, genderObservable) { name, age, dob, gender ->
            // Once last input field (edittext) starts typing.. comrbineLatest gets trigger...
            // it's not considering the spinner part..
            println("log name = $name")
            println("log age = $age")
            println("log dob = $dob")
            println("log gender = $gender")
            isValidForm(name.toString(), age.toString(), dob.toString(), gender)
        }.subscribe {
            buttonSave.isEnabled = it
        }

    }

    private fun setListeners() {
        val cal = Calendar.getInstance()
        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // "myDob" can only be used in context of AppPreferences
                with(AppPreferences) {
                    pref.myDob = cal.timeInMillis
                }
                updateDateInView(cal)
            }
        }
//      Add android:focusable="false" in Edittext to allow for a single touch.
        // if we don't add above line, after two touch only date-picker dialog will come..
        editTextDob.setOnClickListener {
            val dialog = DatePickerDialog(context!!, dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH))
            dialog.datePicker.maxDate = System.currentTimeMillis()
            dialog.show()
        }

        buttonSave.setOnClickListener {

        }


    }

    private fun updateDateInView(cal: Calendar) {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        editTextDob.setText(sdf.format(cal.getTime()), TextView.BufferType.EDITABLE)
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

}