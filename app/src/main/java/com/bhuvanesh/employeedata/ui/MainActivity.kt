package com.bhuvanesh.employeedata

import android.os.Bundle
import androidx.navigation.findNavController
import com.bhuvanesh.employeedata.base.BaseActivity


class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        replace(R.id.fragment_container, EmployeeListFragment.newInstance())
    }

    // if we don't override this method, our fragment won't navigate to back..
    // ERROR: this method not at all get called..
    override fun onSupportNavigateUp(): Boolean  {
        println("log onSupportNavigateUp")
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        var backStackEntryCount = navHostFragment?.childFragmentManager?.backStackEntryCount
        println("log onBackPressed bs count = $backStackEntryCount")
        if (backStackEntryCount == 0)
            finish()

        findNavController(R.id.nav_host_fragment).navigateUp()
    }
}
