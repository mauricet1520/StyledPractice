package com.coolreecedev.styledpractice.ui

import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.coolreecedev.styledpractice.util.LOG_TAG
import com.coolreecedev.styledpractice.R
import kotlinx.android.synthetic.main.zip_code_fragment.*
import kotlinx.android.synthetic.main.zip_code_fragment.view.*

const val REQUEST_CODE = 1001
class ZipCodeFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null

    private lateinit var viewModel: ZipCodeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(ZipCodeViewModel::class.java)

        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.i(
                LOG_TAG, "PERMISSION GRANTED")
        }else {
            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE
            )
        }
        val view = inflater.inflate(R.layout.zip_code_fragment, container, false)

        view.zipCodeimageButton.setOnClickListener {
            editZipCodeId.text.let {
                viewModel.getFireBaseZipCode(editZipCodeId.text.toString())
                viewModel.fireBaseZipCodeData.observe(viewLifecycleOwner, Observer {
                        Log.i(LOG_TAG, "zip code: $it")
                    if (it != null && it) {
                        listener?.onListFragmentInteraction(it, editZipCodeId.text.toString())
                    }else {
                        listener?.onListFragmentInteraction(it, editZipCodeId.text.toString())
                    }
                })
            }


        }

        return view
    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(available: Boolean, zipCode: String?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
