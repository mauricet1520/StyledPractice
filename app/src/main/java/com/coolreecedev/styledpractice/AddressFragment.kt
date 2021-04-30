package com.coolreecedev.styledpractice

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.databinding.FragmentAddressBinding
import com.coolreecedev.styledpractice.databinding.FragmentConfirmationBinding
import com.coolreecedev.styledpractice.databinding.FragmentPricingOptionsBinding
import com.coolreecedev.styledpractice.util.LOG_TAG
import com.google.android.material.textfield.TextInputEditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddressFragment : Fragment() {

    private val args: AddressFragmentArgs by navArgs()
    private lateinit var binding: FragmentAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddressBinding.inflate(inflater, container, false)

        Log.i(LOG_TAG, "appointment: ${args.appointment}")
        Log.i(LOG_TAG, "customer: ${args.customer}")

        val appointment = args.appointment
        val zip = appointment?.zip
        val customer = args.customer

        with(binding.zipcodeEditText) {
            this.setText(zip)
        }
        with(binding.streetAddressEditInput) {

        }

        with(binding.confirmationAddressButton) {
            setOnClickListener {
                appointment?.street_address = binding.streetAddressEditInput.text.toString()
                customer?.address = binding.streetAddressEditInput.text.toString()
                appointment?.city = binding.cityEditInput.text.toString()
                customer?.city = binding.cityEditInput.text.toString()
                appointment?.state = binding.stateEditText.text.toString()
                customer?.state = binding.stateEditText.text.toString()
                customer?.zip = appointment?.zip
                customer?.phone = binding.phoneEditInput.text.toString()

                val action =
                    AddressFragmentDirections.actionAddressFragmentToPaymentFragment(
                        appointment = appointment, customer = customer
                    )
                findNavController().navigate(action)
            }
        }

        return binding.root
    }


}