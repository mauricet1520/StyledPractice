package com.coolreecedev.styledpractice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.databinding.FragmentPricingOptionsBinding
import com.coolreecedev.styledpractice.databinding.FragmentPrintPatternsBinding
import com.coolreecedev.styledpractice.util.LOG_TAG

class PrintPatternsFragment : Fragment() {

    private lateinit var binding: FragmentPrintPatternsBinding
    private val args: PrintPatternsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPrintPatternsBinding.inflate(inflater, container, false)

        Log.i(LOG_TAG, "appointment: ${args.appointment}")
        Log.i(LOG_TAG, "customer: ${args.customer}")

        with(binding.savePrintPatterns) {
            setOnClickListener {
                if (args.customer?.print_patterns.isNullOrEmpty()) {
                    Toast.makeText(context, "Please select one or more patterns", Toast.LENGTH_LONG).show()
                }else {
                    val action =
                        PrintPatternsFragmentDirections.actionPrintPatternsFragmentToColorsFragment(
                            appointment = args.appointment,
                            customer = args.customer
                        )
                    findNavController().navigate(action)
                }
            }
        }

        with(binding.animalCheckbox) {
            getValueIfChecked("Animal")
        }
        with(binding.damaskCheckbox) {
            getValueIfChecked("Damask")
        }
        with(binding.dotsCheckBox) {
            getValueIfChecked("Dots")
        }
        with(binding.floralCheckbox) {
            getValueIfChecked("Floral")
        }
        with(binding.gingamCheckbox){
            getValueIfChecked("Gingam")
        }
        with(binding.houndStoothCheckBox) {
            getValueIfChecked("Houndstooth")
        }
        return binding.root
    }

    private fun CheckBox.getValueIfChecked(printPattern: String) {
        setOnClickListener {
            if (isChecked) {
                args.customer?.print_patterns?.add(printPattern)
            }
        }
    }

}