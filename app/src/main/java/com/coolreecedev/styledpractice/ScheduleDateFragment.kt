package com.coolreecedev.styledpractice

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import com.coolreecedev.styledpractice.data.Appointment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.coolreecedev.styledpractice.data.appointment.StaffTimeSlots

import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var param1: String? = null
private var param2: String? = null
private var param3: String? = null
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
    private lateinit var staffMembers: List<StaffTimeSlots>


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

        with(binding.elevenOneButton) {
            setOnClickListener {
                setAppointment("${param3}T11:00Z", "${param3}T13:00Z")
            }

        }

        with(binding.twelveTwoButton) {
            setOnClickListener {
                setAppointment("${param3}T12:00Z", "${param3}T14:00Z")
            }
        }

        with(binding.oneThreeButton) {
            setOnClickListener {
                setAppointment("${param3}T13:00Z", "${param3}T15:00Z")
            }
        }

        with(binding.twoFourButton) {
            setOnClickListener {
                setAppointment("${param3}T14:00Z", "${param3}T16:00Z")
            }
        }

        with(binding.threeFiveButton) {
            setOnClickListener {
                setAppointment("${param3}T15:00Z", "${param3}T17:00Z")
            }
        }

        with(binding.fourSixButton) {
            setOnClickListener {
                setAppointment("${param3}T16:00Z", "${param3}T18:00Z")
            }
        }

        with(binding.fiveSevenButton) {
            setOnClickListener {
                setAppointment("${param3}T17:00Z", "${param3}T19:00Z")
            }
        }

        with(binding.sixEightButton) {
            setOnClickListener {
                setAppointment("${param3}T18:00Z", "${param3}T20:00Z")
            }
        }

        if (param1 != null) {

            viewModel.getTimeSlotData(param2!!, appointment?.setmore_service_name!!)

            binding.progressBar.visibility = View.VISIBLE

            viewModel.timeSlotData.observe(viewLifecycleOwner, Observer { staffTimeSlots ->
                binding.progressBar.visibility = View.INVISIBLE
                Log.i(LOG_TAG, "StaffTimeSlots size ${staffTimeSlots.size}")
                staffTimeSlots.forEach {
                    if (it.sevenToNineTimeSlotsAvailable) binding.elevenOneButton.visibility =
                        View.VISIBLE
                    if (it.eightToTenTimeSlotsAvailable) binding.twelveTwoButton.visibility =
                        View.VISIBLE
                    if (it.nineToElevenTimeSlotsAvailable) binding.oneThreeButton.visibility =
                        View.VISIBLE
                    if (it.tenToTwelveTimeSlotsAvailable) binding.twoFourButton.visibility =
                        View.VISIBLE
                    if (it.elevenToThirteenTimeSlotsAvailable) binding.threeFiveButton.visibility =
                        View.VISIBLE
                    if (it.twelveToFourteenTimeSlotsAvailable) binding.fourSixButton.visibility =
                        View.VISIBLE
                    if (it.thirteenToFifteenTimeSlotsAvailable) binding.fiveSevenButton.visibility =
                        View.VISIBLE
                    if (it.fourteenToSixteenTimeSlotsAvailable) binding.sixEightButton.visibility =
                        View.VISIBLE
                }

                staffMembers = staffTimeSlots
            })

            binding.imageView12.visibility = View.INVISIBLE
            binding.textDate.visibility = View.VISIBLE

            binding.textDate.text = param3
            Toast.makeText(context, "Date selected: $param1", Toast.LENGTH_LONG).show()
        }


        with(binding.chooseDateTextView) {
            setOnClickListener {
                val newFragment: DialogFragment = DatePickerFragment()
                newFragment.show(parentFragmentManager, "datePicker")

            }

        }

        return binding.root
    }

    private fun Button.setAppointment(startTime: String, endTime: String) {
        Log.i(LOG_TAG, "Setting Appointment time to ${this.text}")
        appointment?.start_time = startTime
        appointment?.end_time = endTime
        val staffTimeSlots = staffMembers.random()
        if (staffTimeSlots.sevenToNineTimeSlotsAvailable) {
            Log.i(
                LOG_TAG,
                "Random StaffMember: ${staffTimeSlots.staff.first_name} is available"
            )
            appointment?.setmore_staff_key = staffTimeSlots.staff.key
        } else {
            run loop@{
                staffMembers.forEach {
                    if (it.sevenToNineTimeSlotsAvailable) {
                        appointment?.setmore_staff_key = it.staff.key
                        Log.i(
                            LOG_TAG,
                            "Searched StaffMember: ${it.staff.first_name} is available"
                        )
                        return@loop
                    }
                }
            }
        }
        clearButtonBackground(this)

        Log.i(
            LOG_TAG,
            "Setting Appointment for staff key ${appointment?.setmore_staff_key}"
        )
        Log.i(
            LOG_TAG,
            "Appointment setmore_service_name: ${appointment?.setmore_service_name}"
        )
        Log.i(
            LOG_TAG,
            "Appointment setmore_service_key: ${appointment?.setmore_service_key}"
        )
        Log.i(LOG_TAG, "Appointment zip: ${appointment?.zip}")
        Log.i(LOG_TAG, "Appointment start_time: ${appointment?.start_time}")
        Log.i(LOG_TAG, "Appointment end_time: ${appointment?.end_time}")
        Log.i(LOG_TAG, "Appointment occasion: ${appointment?.occasion}")
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
            } else {
                month.inc().toString()
            }
            when (c.get(Calendar.DAY_OF_WEEK)) {
                Calendar.SATURDAY -> {
                    param1 = "Saturday"
                    param2 = "$dayDate/$monthDate/$year"
                    param3 = "$year-$monthDate-$dayDate"
                    vargs.putString(ARG_PARAM1, "Saturday")
                    vargs.putString(ARG_PARAM2, "$dayDate/$monthDate/$year")
                    vargs.putParcelable("appointment", appointment)
                }
                Calendar.SUNDAY -> {
                    param1 = "Sunday"
                    param2 = "$dayDate/$monthDate/$year"
                    param3 = "$year-$monthDate-$dayDate"
                    vargs.putString(ARG_PARAM1, "Sunday")
                    vargs.putString(ARG_PARAM2, "$dayDate/$monthDate/$year")
                    vargs.putParcelable("appointment", appointment)

                }
                else -> {
                    param1 = "Weekday "
                    param2 = "$dayDate/$monthDate/$year"
                    param3 = "$year-$monthDate-$dayDate"
                    vargs.putString(ARG_PARAM1, "Weekday ")
                    vargs.putString(ARG_PARAM2, "$dayDate/$monthDate/$year")
                    vargs.putParcelable("appointment", appointment)
                }
            }
            Log.i(LOG_TAG, "Date selected: $dayDate/$monthDate/$year")
            findNavController().navigate(R.id.scheduleDateFragment, vargs)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun clearButtonBackground(button: Button) {
        binding.elevenOneButton.background = resources.getDrawable(R.drawable.button_border)
        binding.twelveTwoButton.background = resources.getDrawable(R.drawable.button_border)
        binding.oneThreeButton.background = resources.getDrawable(R.drawable.button_border)
        binding.twoFourButton.background = resources.getDrawable(R.drawable.button_border)
        binding.threeFiveButton.background = resources.getDrawable(R.drawable.button_border)
        binding.fourSixButton.background = resources.getDrawable(R.drawable.button_border)
        binding.fiveSevenButton.background = resources.getDrawable(R.drawable.button_border)
        binding.sixEightButton.background = resources.getDrawable(R.drawable.button_border)
        button.background = resources.getDrawable(R.color.colorAccent)
    }
}