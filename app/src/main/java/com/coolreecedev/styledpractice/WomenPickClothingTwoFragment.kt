package com.coolreecedev.styledpractice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.data.Appointment
import com.coolreecedev.styledpractice.data.customer.Customer
import com.coolreecedev.styledpractice.databinding.FragmentWomenPickClothingTwoBinding
import com.coolreecedev.styledpractice.util.LOG_TAG

private lateinit var customer: Customer
private lateinit var appointment: Appointment

class WomenPickClothingTwoFragment : Fragment() {

    private lateinit var binding: FragmentWomenPickClothingTwoBinding
     private val args: WomenPickClothingTwoFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWomenPickClothingTwoBinding.inflate(inflater, container, false)

        Log.i(LOG_TAG, "appointment: ${args.appointment}")
        Log.i(LOG_TAG, "customer: ${args.customer}")

        binding.shirtsSpinnerId.onItemSelectedListener = ShirtsSpinnerListener()
        binding.rompersSpinnerId.onItemSelectedListener = RomperSpinnerListener()

        customer = args.customer!!
        appointment = args.appointment!!

        with(binding.womenPickClothingTwoNextButton) {
            setOnClickListener {
                if (customer.shirt_size == null && customer.jumper_romper_size == null) {
                    Toast.makeText(context, "Please choose both sizes", Toast.LENGTH_SHORT).show()
                } else {
                    val action =
                        WomenPickClothingTwoFragmentDirections.actionWomenPickClothingTwoFragmentToWomenPickClothingFragment(
                            appointment = appointment,
                            customer = customer
                        )
                    findNavController().navigate(action)
                }
            }
        }

        return binding.root
    }

    private class RomperSpinnerListener: AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val o: String = parent?.getItemAtPosition(position) as String
            Log.i(LOG_TAG ,"Romper Size: $o")
            customer.jumper_romper_size = o
        }
    }

    private class ShirtsSpinnerListener: AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val o: String = parent?.getItemAtPosition(position) as String
            Log.i(LOG_TAG ,"Shirt Size: $o")
            customer.shirt_size = o
            customer.top_size = o

        }
    }
}