package com.coolreecedev.styledpractice.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.coolreecedev.styledpractice.AppointmentTimeAdapter
import com.coolreecedev.styledpractice.R
import com.coolreecedev.styledpractice.data.availabledate.AvailableDate
import com.coolreecedev.styledpractice.util.LOG_TAG

/**
 * A simple [Fragment] subclass.
 */
class AppointmentFragment : Fragment(), AppointmentTimeAdapter.AppointmentTimeListener {

    private lateinit var navController: NavController
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AvailableDateViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_appointment, container, false)
        recyclerView = view.findViewById(R.id.appointmentRecyclerView)
        viewModel = ViewModelProviders.of(requireActivity()).get(AvailableDateViewModel::class.java)


        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        setHasOptionsMenu(true)
        navController = Navigation.findNavController(
            requireActivity(), R.id.nav_host
        )
        // Inflate the layout for this fragment
        viewModel.selectedAvailableDate.observe(viewLifecycleOwner, Observer {
            Log.i(LOG_TAG, "Selected: $it")
            val adapter = AppointmentTimeAdapter(requireContext(), it, this)
            recyclerView.adapter = adapter
        })

        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home) {
            navController.navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setAppointmentTime(timeSlot: String) {
        Log.i(LOG_TAG, "TimeSlot: $timeSlot")
        val availableDate = viewModel.selectedAvailableDate.value as AvailableDate
        availableDate.timeSlot = arrayListOf(timeSlot)
        viewModel.selectedAvailableDate.value = availableDate
        Log.i(LOG_TAG, "AvailableDate: $availableDate")

        navController.navigate(R.id.paymentTypeFragment)
    }

}
