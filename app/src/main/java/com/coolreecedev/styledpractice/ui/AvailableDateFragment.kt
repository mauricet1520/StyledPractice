package com.coolreecedev.styledpractice.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.coolreecedev.styledpractice.util.LOG_TAG
import com.coolreecedev.styledpractice.MyScheduleRecyclerViewAdapter
import com.coolreecedev.styledpractice.R
import com.coolreecedev.styledpractice.data.availabledate.AvailableDate


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [AvailableDateFragment.OnListFragmentInteractionListener] interface.
 */
class AvailableDateFragment : Fragment(),
MyScheduleRecyclerViewAdapter.AvailableDateListener{

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    private lateinit var viewModel: AvailableDateViewModel

    private lateinit var availableDateList: List<AvailableDate>

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
        val view = inflater.inflate(R.layout.fragment_schedule_list, container, false)
        viewModel = ViewModelProviders.of(requireActivity()).get(AvailableDateViewModel::class.java)
        navController = Navigation.findNavController(
            requireActivity(),R.id.nav_host
        )
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(context, "PERMISSION GRANTED",
//                Toast.LENGTH_SHORT).show()
        }else {
            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1002)
        }

        viewModel.availableDateData.observe(requireActivity(), Observer {
             availableDateList = viewModel.availableDateData.value ?: emptyList()
            if (view is RecyclerView) {
                with(view) {
                    layoutManager = when {
                        columnCount <= 1 -> LinearLayoutManager(context)
                        else -> GridLayoutManager(context, columnCount)
                    }

                    Log.i(LOG_TAG, "AvailableDateList size: ${availableDateList.size}")
                    adapter =
                        MyScheduleRecyclerViewAdapter(
                            availableDateList,
                            listener,
                            this@AvailableDateFragment
                        )
                }
            }
        })
        // Set the adapter
        return view
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: AvailableDate?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            AvailableDateFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    override fun onAvailableDateItemClick(availableDate: AvailableDate) {
        Log.i(LOG_TAG, "Sending $availableDate")
        viewModel.selectedAvailableDate.value = availableDate
        navController.navigate(R.id.appointmentFragment)
    }
}
