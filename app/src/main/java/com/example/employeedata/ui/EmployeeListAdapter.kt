package com.example.employeedata.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.employeedata.database.EmployeeRecord
import example.com.employeedata.R

class EmployeeListAdapter(private val context: Context,
                          private val movieList: MutableList<EmployeeRecord> = mutableListOf()):
    RecyclerView.Adapter<EmployeeListAdapter.EmployeeViewHolder>() {


    public fun setData(movie: EmployeeRecord?) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): EmployeeViewHolder {
        return EmployeeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie_overview, parent, false))
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val item = movieList?.get(position)

        holder.movieTitle.text = item?.title
        holder.movieOverview.text = item?.overview
        holder.movieRating.text = "${item?.voteAverage}"
        holder.movieReleaseDate.text = item?.releaseDate
        holder.movieLang.text = item?.originalLanguage

        holder.rootView.setOnClickListener {
            presenter?.onMovieItemClicked(movieList?.get(holder.adapterPosition)!!)
        }
    }

    override fun getItemCount(): Int {
        return movieList?.size ?: 0
    }

    class EmployeeViewHolder(override val containerView: View?): RecyclerView.ViewHolder(containerView!!)
}