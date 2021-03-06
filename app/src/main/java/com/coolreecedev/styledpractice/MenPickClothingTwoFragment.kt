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
import com.coolreecedev.styledpractice.databinding.FragmentMenPickClothingBinding
import com.coolreecedev.styledpractice.databinding.FragmentMenPickClothingTwoBinding
import com.coolreecedev.styledpractice.databinding.FragmentWomenPickClothingTwoBinding
import com.coolreecedev.styledpractice.util.LOG_TAG

private lateinit var customer: Customer
private lateinit var appointment: Appointment

class MenPickClothingTwoFragment : Fragment() {

    private lateinit var binding: FragmentMenPickClothingTwoBinding
    private val args: MenPickClothingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding = FragmentMenPickClothingTwoBinding.inflate(inflater, container, false)

        binding.blazerSpinnerId.onItemSelectedListener =
            BlazerSpinnerListener()
        binding.pantsSpinnerId.onItemSelectedListener =
            PantsShirtsSpinnerListener()
        customer = args.customer!!
        appointment = args.appointment!!

        with(binding.menPickClothingTwoNextButton) {
            setOnClickListener {
                if (customer.blazer_size == null && customer.pants_size == null) {
                    android.widget.Toast.makeText(context, "Please choose both sizes", android.widget.Toast.LENGTH_SHORT).show()
                } else {
                    val action =
                        MenPickClothingTwoFragmentDirections.actionMenPickClothingTwoFragmentToPrintPatternsFragment(
                            appointment = appointment,
                            customer = customer
                        )
                    findNavController().navigate(action)
                }
            }
        }


        return binding.root


    }

    private class BlazerSpinnerListener: AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val o: String = parent?.getItemAtPosition(position) as String
            Log.i(LOG_TAG ,"Dress Shirt Size: $o")
            customer.blazer_size = o
        }
    }

    private class PantsShirtsSpinnerListener: AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val o: String = parent?.getItemAtPosition(position) as String
            Log.i(LOG_TAG ,"Shirt Size: $o")
            customer.pants_size = o
        }
    }
}