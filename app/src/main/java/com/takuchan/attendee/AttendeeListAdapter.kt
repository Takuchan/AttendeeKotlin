package com.takuchan.attendee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.navigation.findNavController

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.takuchan.attendee.database.AttendeeEntity
import com.takuchan.attendee.ui.dashboard.DashboardFragmentDirections

class AttendeeListAdapter : ListAdapter<AttendeeEntity, AttendeeListAdapter.WordViewHolder>(AttendeeComperator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title,current.location,current.date)
        holder.itemView.setOnClickListener{
            val databaseiD:Int = current.id
            holder.itemView.findNavController().navigate(DashboardFragmentDirections.actionNavigationDashboardToAttendeeItemShowFragment(databaseiD))

        }
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title : TextView = itemView.findViewById(R.id.item_recylcer_title)
        private val location : TextView = itemView.findViewById(R.id.item_recylcer_location)
        private val date: TextView = itemView.findViewById(R.id.item_recylcer_date)
        fun bind(getTitle: String?, getLocation: String?, getDate: String?) {
            title.text = getTitle
            location.text = getLocation
            date.text = getDate
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_attendee_item, parent, false)

                return WordViewHolder(view)
            }
        }
    }

    class AttendeeComperator : DiffUtil.ItemCallback<AttendeeEntity>() {
        override fun areItemsTheSame(oldItem: AttendeeEntity, newItem: AttendeeEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AttendeeEntity, newItem: AttendeeEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }
}

