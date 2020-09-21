package com.coolreecedev.styledpractice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.coolreecedev.styledpractice.util.LOG_TAG

/**
 * A simple [Fragment] subclass.
 */
class InvalidZipCodeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bundle = Bundle()

        val zipCode = savedInstanceState?.getString("zipCode")
        Log.i(LOG_TAG, "zip code: $zipCode")
        Log.i(LOG_TAG, "zip code2: ${bundle.getString("zipCode")}")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invalid_zip_code, container, false)
    }

}
