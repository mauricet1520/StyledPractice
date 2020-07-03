package com.coolreecedev.styledpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.coolreecedev.styledpractice.data.availabledate.AvailableDate
import com.coolreecedev.styledpractice.ui.AvailableDateFragment
import com.coolreecedev.styledpractice.ui.AvailableDateViewModel
import com.coolreecedev.styledpractice.ui.ZipCodeFragment
import com.coolreecedev.styledpractice.util.LOG_TAG

class ActivateClientActivity : AppCompatActivity(),
    AvailableDateFragment.OnListFragmentInteractionListener,
    ZipCodeFragment.OnListFragmentInteractionListener {
    private val mListener: ZipCodeFragment.OnListFragmentInteractionListener? = null



    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is ZipCodeFragment) {
            fragment.onAttach(this)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activate_client)
        findNavController(R.id.nav_host).navigate(R.id.zip_code_dest)



//        val zipCodeFab = findViewById<ExtendedFloatingActionButton>(R.id.zip_code_fab)
//
//        zipCodeFab.setOnClickListener {view ->
//            findNavController(R.id.nav_host).navigate(R.id.schedule_dest)
//        }
    }

//    fun validateZipCode(view: View) {
//        if (editZipCodeId.text == null){
//            Toast.makeText(applicationContext, "You must enter in a zipCode", Toast.LENGTH_SHORT).show()
//        }else {
//            findNavController(R.id.nav_host).navigate(R.id.schedule_dest)
//        }
//    }

    fun navigateTo(frag: Int){
        findNavController(R.id.nav_host).navigate(frag)
    }



    override fun onListFragmentInteraction(available: Boolean) {
        if (available) {
            Toast.makeText(this, "Available", Toast.LENGTH_SHORT).show()
            findNavController(R.id.nav_host).navigate(R.id.schedule_dest)

        }
        else {
            Toast.makeText(this, "UnAvailable", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onListFragmentInteraction(item: AvailableDate?) {
        Log.i(LOG_TAG, "Available Date: $item")
        findNavController(R.id.nav_host).navigate(R.id.action_appointmentFragment)


    }
}
