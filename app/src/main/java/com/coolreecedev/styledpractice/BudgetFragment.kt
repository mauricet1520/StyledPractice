package com.coolreecedev.styledpractice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.data.appointment.AppointmentViewModel
import com.coolreecedev.styledpractice.databinding.FragmentBudgetBinding


class BudgetFragment : Fragment() {

    private lateinit var binding : FragmentBudgetBinding
    private val args: BudgetFragmentArgs by navArgs()
    private lateinit var appointmentViewModel: AppointmentViewModel


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBudgetBinding.inflate(inflater, container, false)

        appointmentViewModel = ViewModelProviders.of(this).get(AppointmentViewModel::class.java)



        with(binding.fiveHundredButton) {
            setOnClickListener {

//                this.background = resources.getDrawable(R.color.colorAccent)
                args.appointment?.budget = "500"
                appointmentViewModel.updateAppointment(args.appointment!!)


                val intent = Intent(context, CameraActivity::class.java)
                startActivity(intent)
            }
        }

        with(binding.threeToFourHundredButton) {
            setOnClickListener {
//                this.background = resources.getDrawable(R.color.colorAccent)
                args.appointment?.budget = "300-499"
                appointmentViewModel.updateAppointment(args.appointment!!)


                val intent = Intent(context, CameraActivity::class.java)
                startActivity(intent)
            }
        }

        with(binding.threeHundredButton) {
            setOnClickListener {
//                this.background = resources.getDrawable(R.color.c
//                olorAccent)
                args.appointment?.budget = "300"
                appointmentViewModel.updateAppointment(args.appointment!!)


                val intent = Intent(context, CameraActivity::class.java)
                startActivity(intent)
            }
        }

        return binding.root
    }
}