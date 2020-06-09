package com.example.employeedata.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.employeedata.AppUtil
import com.example.employeedata.R
import com.example.employeedata.database.EmployeeRecord
import kotlinx.android.synthetic.main.item_employee.view.*

class EmployeeListAdapter(private val context: Context,
                          private var employeeList: MutableList<EmployeeRecord> = mutableListOf(),
                          private val clickListener: (Int, EmployeeRecord) -> Unit):
    RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder>() {

    fun setData(list: List<EmployeeRecord>) {
        employeeList = list as MutableList<EmployeeRecord>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): EmployeeViewHolder {
        return EmployeeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_employee, parent, false))
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val item = employeeList.get(position)
        holder.bind(item, clickListener)
    }

    override fun getItemCount(): Int {
        return employeeList?.size ?: 0
    }

    class EmployeeViewHolder(val containerView: View?): RecyclerView.ViewHolder(containerView!!) {

        fun bind(item: EmployeeRecord, clickListener: (Int, EmployeeRecord) -> Unit) {
            itemView.run {
                textviewName.text = item.name
                textviewDob.text = AppUtil.convertTimeStampToString(item.dob)
                textviewGender.text = when {
                    item.gender == 1 -> "Male"
                    item.gender == 2 -> "Female"
                    else -> ""
                }
                textviewAge.text = item.age.toString()

                imageviewEdit.setOnClickListener { clickListener(R.id.imageviewEdit, item) }
                imageviewDelete.setOnClickListener { clickListener(R.id.imageviewDelete, item) }
            }

        }
    }
}