package com.coolreecedev.styledpractice

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
import com.coolreecedev.styledpractice.data.ZipCode
import com.coolreecedev.styledpractice.dummy.DummyContent
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
            Toast.makeText(context, "PERMISSION GRANTED",
                Toast.LENGTH_SHORT).show()
        }else {
            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
        }
        val view = inflater.inflate(R.layout.zip_code_fragment, container, false)

        view.zip_code_fab.setOnClickListener {
            var available = false
            editZipCodeId.text.let {
                viewModel.getOneZipCode(editZipCodeId.text.toString())
                viewModel.oneZipCodeData.observe(this, Observer {
                        Log.i(LOG_TAG, "zip code: $it")
                    if (it != null) {
                        available = true
                        listener?.onListFragmentInteraction(available)
                    }else {
                        listener?.onListFragmentInteraction(available)
                    }
                })
            }

        }

        return view
    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(available: Boolean)
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



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
