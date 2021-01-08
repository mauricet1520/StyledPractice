package com.coolreecedev.styledpractice

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.databinding.FragmentAddressBinding
import com.coolreecedev.styledpractice.databinding.FragmentConfirmationBinding
import com.coolreecedev.styledpractice.databinding.FragmentPricingOptionsBinding
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
        val appointment = args.appointment
        val zip = appointment?.zip

        with(binding.zipcodeEditText) {
            this.setText(zip)
        }
        with(binding.streetAddressEditInput) {

        }

        with(binding.confirmationAddressButton) {
            setOnClickListener {
                appointment?.street_address = binding.streetAddressEditInput.text.toString()
                appointment?.city = binding.cityEditInput.text.toString()
                appointment?.state = binding.stateEditText.text.toString()
                val action =
                    AddressFragmentDirections.actionAddressFragmentToPaymentFragment(
                        appointment = appointment, customer = args.customer
                    )
                findNavController().navigate(action)
            }
        }

        return binding.root
    }


}