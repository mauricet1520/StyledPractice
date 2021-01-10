package com.coolreecedev.styledpractice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.data.appointment.AppointmentViewModel
import com.coolreecedev.styledpractice.data.customer.CustomerViewModel
import com.coolreecedev.styledpractice.databinding.FragmentColorsBinding
import com.coolreecedev.styledpractice.databinding.FragmentPrintPatternsBinding
import com.coolreecedev.styledpractice.util.LOG_TAG

class ColorsFragment : Fragment() {

    private lateinit var binding: FragmentColorsBinding
    private lateinit var customerViewModel: CustomerViewModel
    private val args: ColorsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentColorsBinding.inflate(inflater, container, false)

        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel::class.java)


        Log.i(LOG_TAG, "appointment: ${args.appointment}")
        Log.i(LOG_TAG, "customer: ${args.customer}")

        with(binding.colorsNextButton) {
            setOnClickListener {
                if (args.customer?.colors.isNullOrEmpty()) {
                    android.widget.Toast.makeText(context, "Please select one or more colors", android.widget.Toast.LENGTH_LONG).show()
                } else {
                    customerViewModel.addCustomer(args.customer!!)
                    val action =
                        ColorsFragmentDirections.actionColorsFragmentToBudgetFragment(
                            appointment = args.appointment,
                            customer = args.customer
                        )
                    findNavController().navigate(action)
                }
            }
        }

        with(binding.blacksCheckBox) {
            getValueIfChecked("blacks")
        }
        with(binding.redsCheckbox) {
            getValueIfChecked("reds")
        }

        with(binding.brownsCheckBox10) {
            getValueIfChecked("browns")
        }

        with(binding.neutralCheckbox) {
            getValueIfChecked("neutral")
        }

        with(binding.brightsCheckBox) {
            getValueIfChecked("brights")
        }

        with(binding.jewelCheckbox) {
            getValueIfChecked("jewel")
        }

        with(binding.pastelCheckbox) {
            getValueIfChecked("pastel")
        }

        with(binding.greensCheckBox) {
            getValueIfChecked("greens")
        }

        return binding.root
    }

    private fun CheckBox.getValueIfChecked(colors: String) {
        setOnClickListener {
            if (isChecked) {
                args.customer?.colors?.add(colors)
            }
        }
    }
}