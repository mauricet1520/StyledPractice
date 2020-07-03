package com.coolreecedev.styledpractice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coolreecedev.styledpractice.data.availabledate.AvailableDate

class AppointmentTimeAdapter(val context: Context,
                             val availableDate: AvailableDate,
                             private val itemListener: AppointmentTimeListener
):
RecyclerView.Adapter<AppointmentTimeAdapter.ViewHolder>(){


    inner class ViewHolder(itemView: View):
    RecyclerView.ViewHolder(itemView){
        val nameText = itemView.findViewById<TextView>(R.id.nameText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.available_time_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = availableDate.timeSlot!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val time = availableDate.timeSlot?.get(position)

        with(holder) {
            nameText?.let {
                it.text = time
                holder.itemView.setOnClickListener {
                    if (time != null) {
                        itemListener.setAppointmentTime(time)
                    }
                }
            }
        }
    }

    interface AppointmentTimeListener {
        fun setAppointmentTime(timeSlot: String)
    }

}