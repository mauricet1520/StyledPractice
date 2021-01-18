package com.coolreecedev.styledpractice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.databinding.FragmentClothesTypeBinding
import com.coolreecedev.styledpractice.databinding.FragmentGetStyledBinding
import com.coolreecedev.styledpractice.databinding.FragmentPaymentBinding
import com.coolreecedev.styledpractice.util.LOG_TAG

class ClothesTypeFragment : Fragment() {

    private lateinit var binding: FragmentClothesTypeBinding
    private val args: ClothesTypeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentClothesTypeBinding.inflate(inflater, container, false)

        with(binding.clothingRadioGroup) {
            setOnCheckedChangeListener { group, checkedId ->

                when (checkedId) {
                    R.id.menClothingType -> {
                        Log.i(LOG_TAG, "Clothing Type: Men's")
                        args.customer?.clothing_type = "men's"
                    }
                    R.id.womenClothingType -> {
                        Log.i(LOG_TAG, "Clothing Type: Women's")
                        args.customer?.clothing_type = "women's"
                    }
                    else -> Log.i(LOG_TAG, "Empty")
                }
            }
        }

        with(binding.chooseClothesTypeId) {
            setOnClickListener {
                val clothingType = args.customer?.clothing_type
                if (clothingType != null) {
                    if(clothingType == "men's") {
                        val action = ClothesTypeFragmentDirections.actionClothesTypeFragmentToBodyTypeMenFragment2(
                            appointment = args.appointment,
                            customer = args.customer
                        )
                        findNavController().navigate(action)
                    }else {
                        val action =
                            ClothesTypeFragmentDirections.actionClothesTypeFragmentToBodyTypeFragment(
                                appointment = args.appointment,
                                customer = args.customer
                            )
                        findNavController().navigate(action)
                    }
                } else {
                    Toast.makeText(context, "Please Select Men's or Women's", Toast.LENGTH_SHORT).show()
                }
            }
        }

        Log.i(LOG_TAG, "ClothesTypeFragment appointmentId: ${args.appointment?.appointment_id}")
        Log.i(LOG_TAG, "ClothesTypeFragment uid: ${args.customer?.uid}")


        return binding.root
    }

}