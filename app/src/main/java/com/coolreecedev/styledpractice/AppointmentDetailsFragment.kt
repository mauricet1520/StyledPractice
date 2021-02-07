package com.coolreecedev.styledpractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.coolreecedev.styledpractice.data.appointment.AppointmentViewModel
import com.coolreecedev.styledpractice.databinding.FragmentAppointmentDetailsBinding
import com.coolreecedev.styledpractice.databinding.FragmentPaymentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AppointmentDetailsFragment : Fragment() {

    private lateinit var appointmentViewModel: AppointmentViewModel
    private lateinit var binding: FragmentAppointmentDetailsBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appointmentViewModel = ViewModelProviders.of(this).get(AppointmentViewModel::class.java)
        binding = FragmentAppointmentDetailsBinding.inflate(inflater, container, false)


        return binding.root
    }
}