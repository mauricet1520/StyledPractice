package com.coolreecedev.styledpractice

import android.app.DatePickerDialog
import android.app.Dialog
import com.coolreecedev.styledpractice.data.Appointment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.data.appointment.AppointmentViewModel
import com.coolreecedev.styledpractice.databinding.FragmentScheduleDateBinding
import com.coolreecedev.styledpractice.util.LOG_TAG
import androidx.lifecycle.Observer

import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var param1: String? = null
private var param2: String? = null
private var appointment: Appointment? = null

/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleDateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleDateFragment : Fragment() {
    // TODO: Rename and change types of parameters


    private lateinit var binding: FragmentScheduleDateBinding
    private val args: ScheduleDateFragmentArgs by navArgs()
    private lateinit var viewModel: AppointmentViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            appointment = it.getParcelable("appointment")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(AppointmentViewModel::class.java)

        binding = FragmentScheduleDateBinding.inflate(inflater, container, false)
        Log.i(LOG_TAG, "SelectedDate Fragment: occasion: ${appointment?.occasion}")
        Log.i(LOG_TAG, "SelectedDate Fragment: service name: ${appointment?.setmore_service_name}")
        Log.i(LOG_TAG, "SelectedDate Fragment: param2: $param2")
        Log.i(LOG_TAG, "SelectedDate Fragment: param1: $param1")


        if(param1 != null) {
            viewModel.getTimeSlotData(param2!!, appointment?.setmore_service_name!!)

            binding.progressBar.visibility = View.VISIBLE

            viewModel.timeSlotData.observe(viewLifecycleOwner, Observer { staffTimeSlots ->
                binding.progressBar.visibility = View.INVISIBLE
                staffTimeSlots.forEach {
                    if (it.sevenToNineTimeSlotsAvailable) binding.elevenOneButton.visibility = View.VISIBLE
                        if (it.eightToTenTimeSlotsAvailable) binding.twelveTwoButton.visibility = View.VISIBLE
                        if (it.nineToElevenTimeSlotsAvailable) binding.oneThreeButton.visibility = View.VISIBLE
                        if (it.tenToTwelveTimeSlotsAvailable) binding.twoFourButton.visibility = View.VISIBLE
                        if (it.elevenToThirteenTimeSlotsAvailable) binding.threeFiveButton.visibility = View.VISIBLE
                        if (it.twelveToFourteenTimeSlotsAvailable) binding.fourSixButton.visibility = View.VISIBLE
                        if (it.thirteenToFifteenTimeSlotsAvailable) binding.fiveSevenButton.visibility = View.VISIBLE
                        if (it.fourteenToSixteenTimeSlotsAvailable) binding.sixEightButton.visibility = View.VISIBLE
                    }
            })

            binding.imageView12.visibility = View.INVISIBLE
            binding.textDate.visibility = View.VISIBLE
            binding.textDate.text = param2
            Toast.makeText(context, "Date selected: $param1", Toast.LENGTH_LONG).show()
//            showTime(param1, param2)
        }


        with(binding.chooseDateTextView) {
            setOnClickListener {
                val newFragment: DialogFragment = DatePickerFragment()
                newFragment.show(parentFragmentManager, "datePicker")

            }

        }

        return binding.root
    }

      private fun showTime(param1: String?, param2: String?) {

          Log.i(LOG_TAG, "Showing times")

          val text_date = binding.textDate
          val oneThreeButton = binding.oneThreeButton
          val twelveTwoButton = binding.twelveTwoButton
          val threeFiveButton = binding.threeFiveButton
          val button = binding.fourSixButton
            val button2 = binding.elevenOneButton
            val button3 = binding.twoFourButton
            val button4 = binding.fiveSevenButton
            val sixEightButton = binding.sixEightButton
            text_date.text = param2
            text_date.visibility = View.VISIBLE

          button.visibility = View.VISIBLE
          button2.visibility = View.VISIBLE
          button3.visibility = View.VISIBLE
          button4.visibility = View.VISIBLE
          sixEightButton.visibility = View.VISIBLE
          oneThreeButton.visibility = View.VISIBLE
          twelveTwoButton.visibility = View.VISIBLE
          threeFiveButton.visibility = View.VISIBLE



//            when (param1) {
//                "Saturday" -> {
//                    button.visibility = View.VISIBLE
//                    button2.visibility = View.VISIBLE
//                    button3.visibility = View.VISIBLE
//                    button4.visibility = View.VISIBLE
//                    button7.visibility = View.INVISIBLE
//                    Toast.makeText(context, "Date selected: $param1", Toast.LENGTH_LONG).show()
//
//                }
//                "Sunday" -> {
//                    Toast.makeText(context, "Sorry no schedule for Sundays", Toast.LENGTH_LONG)
//                        .show()
//                    button.visibility = View.INVISIBLE
//                    button2.visibility = View.INVISIBLE
//                    button3.visibility = View.INVISIBLE
//                    button4.visibility = View.INVISIBLE
//                    button7.visibility = View.INVISIBLE
//                }
//                else -> {
//                    button.visibility = View.INVISIBLE
//                    button2.visibility = View.INVISIBLE
//                    button3.visibility = View.INVISIBLE
//                    button4.visibility = View.INVISIBLE
//                    button7.visibility = View.VISIBLE
//                    Toast.makeText(context, "Date selected: $param1", Toast.LENGTH_LONG).show()
//                }
//            }
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
        fun newInstance(param1: String, param2: String, appointment: Appointment) =
            ScheduleDateFragment().apply {
                arguments = Bundle().apply {
                    Log.i(LOG_TAG, "Creating a new instance of ScheduleDateFragment")
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putParcelable("appointment", appointment)
                }
            }
    }

    class DatePickerFragment : DialogFragment(),
        DatePickerDialog.OnDateSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            // Use the current date as the default date in the picker
            val c = Calendar.getInstance()
            val year = c[Calendar.YEAR]
            val month = c[Calendar.MONTH]
            val day = c[Calendar.DAY_OF_MONTH]


            // Create a new instance of DatePickerDialog and return it
            return DatePickerDialog(requireActivity(), this, year, month, day)
        }

        override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {


            val vargs = Bundle()
            var monthDate = ""
            var dayDate = ""
            val c = Calendar.getInstance()
            c.set(year, month, day)

            dayDate = if (day < 10) {
                "0$day"
            } else {
                day.toString()
            }
            monthDate = if (month.inc() < 10) {
                "0${month.inc()}"
            }else {
                month.inc().toString()
            }
            when (c.get(Calendar.DAY_OF_WEEK)) {
                Calendar.SATURDAY -> {
                    param1 = "Saturday"
                   param2 ="$dayDate/$monthDate/$year"
                    vargs.putString(ARG_PARAM1, "Saturday")
                    vargs.putString(ARG_PARAM2, "$dayDate/$monthDate/$year")
                    vargs.putParcelable("appointment", appointment)
                }
                Calendar.SUNDAY -> {
                    param1 = "Sunday"
                    param2 ="$dayDate/$monthDate/$year"
                    vargs.putString(ARG_PARAM1, "Sunday")
                    vargs.putString(ARG_PARAM2, "$dayDate/$monthDate/$year")
                    vargs.putParcelable("appointment", appointment)

                }
                else -> {
                    param1 = "Weekday "
                    param2 ="$dayDate/$monthDate/$year"
                    vargs.putString(ARG_PARAM1, "Weekday ")
                    vargs.putString(ARG_PARAM2, "$dayDate/$monthDate/$year")
                    vargs.putParcelable("appointment", appointment)
                }
            }
            Log.i(LOG_TAG, "Date selected: $dayDate/$monthDate/$year")
        findNavController().navigate(R.id.scheduleDateFragment, vargs)
        }
    }
}