package com.coolreecedev.styledpractice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.data.Appointment
import com.coolreecedev.styledpractice.data.customer.Customer
import com.coolreecedev.styledpractice.databinding.FragmentBodyTypeBinding
import com.coolreecedev.styledpractice.databinding.FragmentWomenClothingBinding
import com.coolreecedev.styledpractice.databinding.FragmentWomenPickClothingBinding
import com.coolreecedev.styledpractice.util.LOG_TAG


private lateinit var customer: Customer
private lateinit var appointment: Appointment

class WomenPickClothingFragment : Fragment() {

    private lateinit var binding: FragmentWomenPickClothingBinding
    private val args: WomenPickClothingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentWomenPickClothingBinding.inflate(inflater, container, false)

        Log.i(LOG_TAG, "appointment: ${args.appointment}")
        Log.i(LOG_TAG, "customer: ${args.customer}")

        binding.dressSpinnerId.onItemSelectedListener =
            DressSpinnerListener()
        binding.pantSpinnerId.onItemSelectedListener =
            PantSpinnerListener()

        customer = args.customer!!
        appointment = args.appointment!!

        with(binding.womenPickClothingNextButton) {
            setOnClickListener {
                if (customer.dress_size == null && customer.pants_size == null) {
                    android.widget.Toast.makeText(
                        context,
                        "Please choose both sizes",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val action =
                        WomenPickClothingFragmentDirections.actionWomenPickClothingFragmentToPrintPatternsFragment(
                            appointment = appointment,
                            customer = customer
                        )
                    findNavController().navigate(action)
                }
            }
        }

        return binding.root
    }

    private class DressSpinnerListener: AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val o: String = parent?.getItemAtPosition(position) as String
            Log.i(LOG_TAG ,"Dress Size: $o")
            customer.dress_size = o
        }
    }

    private class PantSpinnerListener: AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val o: String = parent?.getItemAtPosition(position) as String
            Log.i(LOG_TAG ,"Pant Size: $o")
            customer.pants_size = o
            customer.bottom_size = o

        }
    }
}