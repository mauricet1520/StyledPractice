package com.coolreecedev.styledpractice

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.contentValuesOf
import com.bumptech.glide.Glide
import com.coolreecedev.styledpractice.data.AppointmentDTO

import com.coolreecedev.styledpractice.dummy.DummyContent.DummyItem
import com.coolreecedev.styledpractice.util.SEASONAL_SERVICE_PHOTO_URL
import com.coolreecedev.styledpractice.util.SHOP_STYLE_URL
import com.coolreecedev.styledpractice.util.SHOP_YOUR_CLOSET_URL
import com.coolreecedev.styledpractice.util.VIRTUAL_SERVICE_PHOTO_URL

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyCustomerAppointmentRecyclerViewAdapter(val context: Context,
                                               private val values: List<AppointmentDTO>,
                                               val itemListener: CustomerAppointmentItemListener
) : RecyclerView.Adapter<MyCustomerAppointmentRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.setmore_service_name
        holder.contentView.text = item.user_appointment_time
        when(item.setmore_service_name) {
            "Shop & Style" -> loadPhotoUrl(item, holder, SHOP_STYLE_URL)
            "Shop Your Closet" -> loadPhotoUrl(item, holder, SHOP_YOUR_CLOSET_URL)
            "Virtual Service" -> loadPhotoUrl(item, holder, VIRTUAL_SERVICE_PHOTO_URL)
            "Seasonal Style" -> loadPhotoUrl(item, holder, SEASONAL_SERVICE_PHOTO_URL)
        }

        holder.itemView.setOnClickListener {
            itemListener.onItemClick(item)

        }
    }

    private fun loadPhotoUrl(
        item: AppointmentDTO,
        holder: ViewHolder,
        serviceUrl: String

    ) {
        Glide.with(context)
            .load(item.imageUrl + serviceUrl)
            .into(holder.styleImage)

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.nameText)
        val contentView: TextView = view.findViewById(R.id.contentView)
        val styleImage: ImageView = view.findViewById(R.id.monsterImage)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    interface CustomerAppointmentItemListener {
        fun onItemClick(appointmentDTO: AppointmentDTO)
    }
}