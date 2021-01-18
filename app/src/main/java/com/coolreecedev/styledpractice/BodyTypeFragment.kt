package com.coolreecedev.styledpractice

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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

        with(binding.womenPickClothingNextButton) {
            setOnClickListener {
                val action =
                    BodyTypeFragmentDirections.actionBodyTypeFragmentToWomenPickClothingTwoFragment(
                        appointment = args.appointment,
                        customer = args.customer
                    )
                findNavController().navigate(action)
            }

        }


        with(binding.athleticId) {
            saveBodyType("Athletic")
        }

        with(binding.averageId) {
            saveBodyType("Average")
        }

        with(binding.curvyId) {
            saveBodyType("Curvy")
        }

        with(binding.pearId) {
            saveBodyType("Pear")
        }

        with(binding.curvyId) {
            saveBodyType("Curvy")
        }

        with(binding.petiteId) {
            saveBodyType("Petite")
        }

        with(binding.maternityId) {
            saveBodyType("Maternity")
        }

        with(binding.slimId) {
            saveBodyType("Slim")
        }

        with(binding.fullFiguredId) {
            saveBodyType("Full Figured")
        }
        return binding.root
    }

    private fun ImageButton.saveBodyType(type: String) {
        setOnClickListener {
            args.customer?.body_type?.add(type)
            background = resources.getDrawable(R.color.colorAccent)
        }
    }

}