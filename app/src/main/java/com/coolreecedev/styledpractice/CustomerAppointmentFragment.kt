package com.coolreecedev.styledpractice

import android.content.Intent
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
import com.coolreecedev.styledpractice.data.Appointment
import com.coolreecedev.styledpractice.data.AppointmentDTO
import com.coolreecedev.styledpractice.data.appointment.AppointmentViewModel
import com.coolreecedev.styledpractice.data.customer.Customer
import com.coolreecedev.styledpractice.data.customer.CustomerViewModel
import com.coolreecedev.styledpractice.dummy.DummyContent
import com.coolreecedev.styledpractice.ui.AvailableDateViewModel
import com.coolreecedev.styledpractice.util.LOG_TAG

/**
 * A fragment representing a list of Items.
 */
class CustomerAppointmentFragment : Fragment(), MyCustomerAppointmentRecyclerViewAdapter.CustomerAppointmentItemListener {
    private lateinit var customerViewModel: CustomerViewModel
    private lateinit var appointmentViewModel: AppointmentViewModel
    private lateinit var customer: Customer
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
                customer = Customer(
                    uid = cust.uid,
                    setmore_customer_id = cust.setmore_customer_id,
                    state = cust.state,
                    shirt_size = cust.shirt_size,
                    stripe_customer_id = cust.stripe_customer_id,
                    blazer_size = cust.blazer_size,
                    body_type = cust.body_type,
                    bottom_size = cust.bottom_size,
                    hubspot_customer_id = cust.hubspot_customer_id,
                    jumper_romper_size = cust.jumper_romper_size,
                    first_name = cust.first_name,
                    last_name = cust.last_name,
                    preferred_stores = cust.preferred_stores,
                    product_ids = cust.product_ids,
                    colors = cust.colors,
                    top_size = cust.top_size,
                    pants_size = cust.pants_size,
                    phone = cust.phone,
                    appointment_ids = cust.appointment_ids,
                    city = cust.city,
                    clothing_type = cust.clothing_type,
                    address = cust.address,
                    image_url = cust.image_url,
                    email = cust.email,
                    dress_shirt_size = cust.dress_shirt_size,
                    dress_size = cust.dress_size
                )
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

                        adapter = MyCustomerAppointmentRecyclerViewAdapter(requireContext(),
                            listOf(it),
                            this@CustomerAppointmentFragment )
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

    override fun onItemClick(appointmentDTO: AppointmentDTO) {
        val appointment = Appointment(
            appointment_id = appointmentDTO.appointment_id,
            street_address = appointmentDTO.street_address,
            city = appointmentDTO.city,
            zip = appointmentDTO.zip,
            state = appointmentDTO.state,
            cost = appointmentDTO.cost,
            customer_id = appointmentDTO.customer_id,
            setmore_customer_key = appointmentDTO.setmore_customer_key,
            setmore_service_key = appointmentDTO.setmore_service_key,
            setmore_appointment_key = appointmentDTO.setmore_appointment_key,
            setmore_label = appointmentDTO.setmore_label,
            start_time = appointmentDTO.start_time,
            occasion = appointmentDTO.occasion,
            setmore_staff_key = appointmentDTO.setmore_staff_key,
            setmore_service_name = appointmentDTO.setmore_service_name,
            status = appointmentDTO.status,
            user_appointment_time = appointmentDTO.user_appointment_time,
            stylist_id = appointmentDTO.stylist_id,
            product_ids = appointmentDTO.product_ids,
            image_url = appointmentDTO.imageUrl,
            budget = appointmentDTO.budget
        )
        Log.i(LOG_TAG, "Appointment id: ${appointment.appointment_id}")
        Log.i(LOG_TAG, "Customer id: ${customer.uid}")
        val intent = Intent(context, ProfileAppointmentActivity::class.java)
        intent.putExtra("appointment", appointment)
        intent.putExtra("customer", customer)
        startActivity(intent)
//        val action = CustomerAppointmentFragmentDirections
//            .actionCustomerAppointmentFragmentToAppointmentDetailsFragment(appointment)
//        findNavController().navigate(action)
    }
}