package com.coolreecedev.styledpractice

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.coolreecedev.styledpractice.ui.AvailableDateFragment.OnListFragmentInteractionListener
import com.coolreecedev.styledpractice.data.availabledate.AvailableDate
import com.coolreecedev.styledpractice.dummy.DummyContent.DummyItem

import kotlinx.android.synthetic.main.fragment_schedule.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyScheduleRecyclerViewAdapter(
    private val mValues: List<AvailableDate>,
    private val mListener: OnListFragmentInteractionListener?,
    private val itemListener: AvailableDateListener
) : RecyclerView.Adapter<MyScheduleRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as AvailableDate
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item._id
        holder.mContentView.text = item.dayOfWeek

        with(holder.mView) {
            tag = item
            holder.mView.setOnClickListener {
                itemListener.onAvailableDateItemClick(item)
            }
//            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }

    interface AvailableDateListener {
        fun onAvailableDateItemClick(availableDate: AvailableDate)
    }
}
