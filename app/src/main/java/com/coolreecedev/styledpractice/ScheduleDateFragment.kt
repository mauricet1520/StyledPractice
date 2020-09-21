package com.coolreecedev.styledpractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_schedule_date.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_schedule_date, container, false)

        if (param1 != null) {
            val text_date = view.findViewById<TextView>(R.id.text_date)
            val button = view.findViewById<Button>(R.id.button)
            val button2 = view.findViewById<Button>(R.id.button2)
            val button3 = view.findViewById<Button>(R.id.button3)
            val button4 = view.findViewById<Button>(R.id.button4)
            val button7 = view.findViewById<Button>(R.id.button7)
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
        return view
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