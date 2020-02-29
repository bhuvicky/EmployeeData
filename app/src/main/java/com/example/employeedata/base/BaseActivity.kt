package com.example.employeedata.base

import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    private val fm = supportFragmentManager

    fun replace(containerId: Int, fragment: BaseFragment) {
        val ft = fm.beginTransaction()
        ft.replace(containerId, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }

    fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onBackPressed() {
        if (fm.backStackEntryCount > 1) {
            fm.popBackStack()
        } else {
            finish()
        }
    }
}