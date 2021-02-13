package com.coolreecedev.styledpractice

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.coolreecedev.styledpractice.data.Appointment
import com.coolreecedev.styledpractice.data.appointment.AppointmentViewModel
import com.coolreecedev.styledpractice.data.customer.Customer
import com.coolreecedev.styledpractice.util.LOG_TAG
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_profile_appointment.*
import kotlinx.android.synthetic.main.content_scrolling.*
import java.io.File
import java.io.FileInputStream

class ProfileAppointmentActivity : AppCompatActivity() {
    private lateinit var appointmentViewModel: AppointmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_appointment)
        setSupportActionBar(findViewById(R.id.toolbar))


        appointmentViewModel = ViewModelProviders.of(this).get(AppointmentViewModel::class.java)

        val appointment = intent.getParcelableExtra<Appointment>("appointment")
        val customer = intent.getParcelableExtra<Customer>("customer")
        val bodyTypeStringBuilder = StringBuilder()
        customer?.body_type?.forEach {
            bodyTypeStringBuilder.append("_")
            bodyTypeStringBuilder.append(it)
        }

        val title = customer?.first_name
        setTitle(title)
        val address = "${customer?.address} ${customer?.city} ${customer?.state}" +
                " ${appointment?.zip}"

        profile_firstName.text = customer?.first_name
        profile_lastName.text = customer?.last_name
        profile_address.text = address
        profile_city.text = customer?.city
        profile_state.text = customer?.state
        profile_zip.text = appointment?.zip
        profile_bodyType.text = bodyTypeStringBuilder.toString()
        profile_email.text = customer?.email
        profile_height.text = customer?.height
        profile_phoneNumber.text = customer?.phone
        profile_event.text = appointment?.occasion
        profile_budget.text = appointment?.budget
        profile_gender.text = customer?.clothing_type


        val sizes = StringBuilder()
        sizes.append("Blazer size: ${customer?.blazer_size ?: ""} \n")
        sizes.append("Shirt size: ${customer?.shirt_size ?: ""} \n")
        sizes.append("Top size: ${customer?.top_size ?: ""} \n")
        sizes.append("Bottom size: ${customer?.bottom_size ?: ""} \n")
        sizes.append("Pant size: ${customer?.pants_size ?: ""} \n")
        sizes.append("JumperRomper size: ${customer?.jumper_romper_size ?: ""} \n")
        profile_size.text = sizes.toString()


        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
//        Toast.makeText(baseContext,"${appointment?.appointment_id}", Toast.LENGTH_SHORT).show()
        Toast.makeText(baseContext,"${customer?.uid}", Toast.LENGTH_SHORT).show()


        val user = FirebaseAuth.getInstance().currentUser
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val imageView = findViewById<ImageView>(R.id.profile_image)

        val pathReference =
            storageRef.child("customer/images/${customer?.uid}.jpg")

        Log.i(
            LOG_TAG, pathReference.path)


        Glide.with(this /* context */)
            .load(pathReference)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(profile_image)


        Glide.with(this /* context */)
            .load(pathReference)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(imageView2)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "${customer.uid}", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}