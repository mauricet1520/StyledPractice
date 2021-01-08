package com.coolreecedev.styledpractice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        Log.i(LOG_TAG, "ClothesTypeFragment appointmentId: ${args.appointment?.appointment_id}")
        Log.i(LOG_TAG, "ClothesTypeFragment uid: ${args.customer?.uid}")
        return binding.root
    }

}