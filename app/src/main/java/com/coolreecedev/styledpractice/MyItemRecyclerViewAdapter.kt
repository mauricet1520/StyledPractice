package com.coolreecedev.styledpractice

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.coolreecedev.styledpractice.data.product.Product

import com.coolreecedev.styledpractice.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val values: List<Product>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_products, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        val itemCount = position + 1
        holder.idView.text = itemCount.toString()
        holder.itemName.text = item.name
        holder.itemType.text = item.item_type
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val itemName: TextView = view.findViewById(R.id.item_name)
        val itemType: TextView = view.findViewById(R.id.item_type)

        override fun toString(): String {
            return super.toString() + " '" + itemName.text + "'"
        }
    }
}