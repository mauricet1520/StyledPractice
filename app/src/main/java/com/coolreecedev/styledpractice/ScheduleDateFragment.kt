package com.coolreecedev.styledpractice

import com.coolreecedev.styledpractice.data.Appointment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.databinding.FragmentScheduleDateBinding
import com.coolreecedev.styledpractice.util.LOG_TAG

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleDateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleDateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var appointment: Appointment? = null

    private lateinit var binding: FragmentScheduleDateBinding
    private val args: ScheduleDateFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
//            appointment = it.getParcelable("appointment")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScheduleDateBinding.inflate(inflater, container, false)
        Log.i(LOG_TAG, "SelectedDate Fragment: occasion: ${appointment?.occasion}")



        if (param1 != null) {

            val text_date = binding.textDate
            val button = binding.eightTenButton
            val button2 = binding.elevenOneButton
            val button3 = binding.twoFourButton
            val button4 = binding.fiveSevenButton
            val button7 = binding.sevenNineButton
            text_date.text = param2
            text_date.visibility = View.VISIBLE




            when(param1) {
                "Saturday"-> {
                    button.visibility = View.VISIBLE
                    button2.visibility = View.VISIBLE
                    button3.visibility = View.VISIBLE
                    button4.visibility = View.VISIBLE
                    button7.visibility = View.INVISIBLE
                    Toast.makeText(context, "Date selected: $param1",Toast.LENGTH_LONG ).show()

                }
                "Sunday"-> {
                    Toast.makeText(context, "Sorry no schedule for Sundays",Toast.LENGTH_LONG ).show()
                    button.visibility = View.INVISIBLE
                    button2.visibility = View.INVISIBLE
                    button3.visibility = View.INVISIBLE
                    button4.visibility = View.INVISIBLE
                    button7.visibility = View.INVISIBLE
                }
                else -> {
                    button.visibility = View.INVISIBLE
                    button2.visibility = View.INVISIBLE
                    button3.visibility = View.INVISIBLE
                    button4.visibility = View.INVISIBLE
                    button7.visibility = View.VISIBLE
                    Toast.makeText(context, "Date selected: $param1",Toast.LENGTH_LONG ).show()
                }
            }
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScheduleDateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScheduleDateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}