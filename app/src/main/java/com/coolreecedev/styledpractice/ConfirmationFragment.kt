package com.coolreecedev.styledpractice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.databinding.FragmentConfirmationBinding
import com.coolreecedev.styledpractice.util.LOG_TAG
import kotlinx.android.synthetic.main.fragment_confirmation.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ConfirmationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfirmationFragment : Fragment() {

    private val args: ConfirmationFragmentArgs by navArgs()
    private lateinit var binding: FragmentConfirmationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConfirmationBinding.inflate(inflater, container, false)

        val confirmationText = "Your appointment is scheduled for ${args.appointment?.user_appointment_time}"

        Log.i(LOG_TAG, "ConfirmationFragment Fragment: customer Id: ${args.customer?.uid}")
        Log.i(LOG_TAG, "ConfirmationFragment Fragment: customer firstName: ${args.customer?.first_name}")
        binding.confirmationDateTextView.text = confirmationText

        val appointment = args.appointment

        with(binding.confirmationDateButton) {
            setOnClickListener {
                val action =
                    ConfirmationFragmentDirections.actionConfirmationFragmentToAddressFragment(appointment, args.customer)
                findNavController().navigate(action)
            }
        }

        return binding.root
    }
}