package com.coolreecedev.styledpractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth

class AppointmentLookupFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val user = FirebaseAuth.getInstance().currentUser

        return inflater.inflate(R.layout.fragment_appointment_lookup, container, false)
    }

}