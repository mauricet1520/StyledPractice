package com.coolreecedev.styledpractice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.data.appointment.AppointmentViewModel
import com.coolreecedev.styledpractice.data.customer.CustomerViewModel
import com.coolreecedev.styledpractice.dummy.DummyContent
import com.coolreecedev.styledpractice.ui.AvailableDateViewModel
import com.coolreecedev.styledpractice.util.LOG_TAG

/**
 * A fragment representing a list of Items.
 */
class CustomerAppointmentFragment : Fragment() {
    private lateinit var customerViewModel: CustomerViewModel
    private lateinit var appointmentViewModel: AppointmentViewModel
    private var columnCount = 1
    private val args: CustomerAppointmentFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel::class.java)
        appointmentViewModel = ViewModelProviders.of(this).get(AppointmentViewModel::class.java)

        customerViewModel.getCustomerAppointment(args.uuid!!)


        customerViewModel.customerAppointmentData.observe(viewLifecycleOwner, Observer {cust ->

            if (cust != null) {
                cust.appointment_ids!!.forEach {
                    appointmentViewModel.getAppointment(it)
                }
            } else {
                findNavController().navigateUp()
                Log.i(LOG_TAG, "No Appointment available")
                Toast.makeText(context, "No Appointment available", Toast.LENGTH_SHORT).show()
            }
        })

        appointmentViewModel.appointmentData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Log.i(LOG_TAG, "${it.appointment_id}")
                if (view is RecyclerView) {
                    with(view) {
                        layoutManager = GridLayoutManager(requireContext(), 2)
//                        layoutManager = when {
//                            columnCount <= 1 -> LinearLayoutManager(context)
//                            else -> GridLayoutManager(context, columnCount)
//                        }

                        adapter = MyCustomerAppointmentRecyclerViewAdapter(requireContext(), listOf(it))
                    }
                }
            } else {
                findNavController().navigateUp()
                Log.i(LOG_TAG, "No Appointments available")
                Toast.makeText(context, "No Appointments available", Toast.LENGTH_SHORT).show()
            }

        })
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            CustomerAppointmentFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}