package com.coolreecedev.styledpractice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coolreecedev.styledpractice.data.Appointment
import com.coolreecedev.styledpractice.data.customer.Customer
import com.coolreecedev.styledpractice.data.product.Product
import com.coolreecedev.styledpractice.databinding.FragmentCheckoutBinding
import com.coolreecedev.styledpractice.util.LOG_TAG
import java.time.LocalDateTime
import java.util.*
import kotlin.math.cos

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val APPOINTMENT = "appointment"
private const val CUSTOMER = "customer"
private const val TRANSACTION_NUMBER = "transaction_number"


class CheckoutFragment : Fragment() {
    private var appointment: Appointment? = null
    private var customer: Customer? = null
    private var transaction_number: String? = null

    private lateinit var binding: FragmentCheckoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            appointment = it.getParcelable(APPOINTMENT)
            customer = it.getParcelable(CUSTOMER)
            transaction_number = it.getString(TRANSACTION_NUMBER)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCheckoutBinding.inflate(inflater, container, false)

        Log.i(LOG_TAG, "appointmentId: ${appointment?.appointment_id}")
        Log.i(LOG_TAG, "customerId: ${customer?.uid}")

        with(binding.saveProductButton) {
            setOnClickListener {
                val sku = binding.skuTextInputEditText.text.toString()
                val productName = binding.itemNameTextInputEditText.text.toString()
                val cost = binding.costNameTextInputEditText.text.toString().toLong()

                Product(
                    cost = cost,
                    transaction_number = transaction_number,
                    firebase_appointment_id = appointment?.appointment_id,
                    firebase_customer_id = customer?.uid,
                    setmore_staff_key = appointment?.setmore_staff_key,
                    sku_number = sku,
                    setmore_appointment_key = appointment?.setmore_appointment_key,
                    setmore_service_key = appointment?.setmore_service_key,
                    setmore_customer_key = appointment?.setmore_customer_key,
                    store_name = binding.storeNameTextInputEditText.text.toString(),
                    item_type = binding.typeTextInputEditText.text.toString(),
                    name = binding.itemNameTextInputEditText.text.toString(),
                    sku_image_url = "${sku}.jpg",
                    item_image_url = "$productName _$cost.jpg"
                )
                binding.costNameTextInputEditText
            }
        }

        binding.itemNameTextInputEditText.text

        return binding.root
    }
}