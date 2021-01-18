package com.coolreecedev.styledpractice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.data.Appointment
import com.coolreecedev.styledpractice.data.customer.Customer
import com.coolreecedev.styledpractice.databinding.FragmentMenPickClothingBinding
import com.coolreecedev.styledpractice.databinding.FragmentWomenPickClothingTwoBinding
import com.coolreecedev.styledpractice.util.LOG_TAG

private lateinit var customer: Customer
private lateinit var appointment: Appointment

class MenPickClothingFragment : Fragment() {
    private lateinit var binding: FragmentMenPickClothingBinding
    private val args: MenPickClothingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.tshirtsSpinnerId.onItemSelectedListener = TeeShirtsSpinnerListener()
        binding.dressShirtSpinnerId.onItemSelectedListener = DressShirtSpinnerListener()
        binding = FragmentMenPickClothingBinding.inflate(inflater, container, false)

        customer = args.customer!!
        appointment = args.appointment!!

        return binding.root
    }

    private class DressShirtSpinnerListener: AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val o: String = parent?.getItemAtPosition(position) as String
            Log.i(LOG_TAG ,"Dress Shirt Size: $o")
            customer.dress_shirt_size = o
        }
    }

    private class TeeShirtsSpinnerListener: AdapterView.OnItemSelectedListener {
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