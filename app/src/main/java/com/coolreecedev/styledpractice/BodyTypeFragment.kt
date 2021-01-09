package com.coolreecedev.styledpractice

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.databinding.FragmentBodyTypeBinding
import com.coolreecedev.styledpractice.databinding.FragmentClothesTypeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BodyTypeFragment : Fragment() {

    private lateinit var binding: FragmentBodyTypeBinding
    private val args: BodyTypeFragmentArgs by navArgs()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentBodyTypeBinding.inflate(inflater, container, false)


        with(binding.athleticId) {

            val customer = args.customer
            setOnClickListener {

                customer?.body_type?.add("Athletic")
                background = resources.getDrawable(R.color.colorAccent)

                    val action =
                        BodyTypeFragmentDirections.actionBodyTypeFragmentToWomenPickClothingTwoFragment(
                            appointment = args.appointment,
                            customer = customer
                        )
                    findNavController().navigate(action)
            }
        }
        return binding.root
    }

}