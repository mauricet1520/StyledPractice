package com.coolreecedev.styledpractice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.coolreecedev.styledpractice.data.Appointment
import com.coolreecedev.styledpractice.data.customer.Customer
import com.coolreecedev.styledpractice.data.product.Product
import com.coolreecedev.styledpractice.data.product.ProductViewModel
import com.coolreecedev.styledpractice.data.product.Transaction
import com.coolreecedev.styledpractice.databinding.FragmentCheckoutBinding
import com.coolreecedev.styledpractice.util.LOG_TAG
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import java.time.LocalDateTime
import java.util.*
import kotlin.math.cos

private const val APPOINTMENT = "appointment"
private const val CUSTOMER = "customer"
private const val TRANSACTION_NUMBER = "transaction_number"
private const val SKU_NUMBER = "sku_number"


class CheckoutFragment : Fragment() {
    private var appointment: Appointment? = null
    private var customer: Customer? = null
    private var transaction_number: String? = null
    private lateinit var productViewModel: ProductViewModel


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
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)


        Log.i(LOG_TAG, "appointmentId: ${appointment?.appointment_id}")
        Log.i(LOG_TAG, "customerId: ${customer?.uid}")
        Log.i(LOG_TAG, "transaction_number: $transaction_number")

        with(binding.saveProductButton) {
            setOnClickListener {
                val sku = binding.skuTextInputEditText.text.toString()
                val productName = binding.itemNameTextInputEditText.text.toString()
                val cost = binding.costNameTextInputEditText.text.toString().toDouble()

                val product =
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
                    sku_image_url = "gs://styled-by-love-e-qa.appspot.com/customer/products/_$sku _$transaction_number.jpg",
                    item_image_url = "gs://styled-by-love-e-qa.appspot.com/customer/sku/_$sku _$transaction_number.jpg",
                    firebase_stylist_id = appointment?.setmore_staff_key
                )

                val transaction = Transaction(transaction_number!!, 0.0, mutableListOf(product))
                productViewModel.saveProductInTransaction(transaction)
                val bundle = Bundle()
                bundle.putParcelable(APPOINTMENT, appointment)
                bundle.putParcelable(CUSTOMER, customer)

                bundle.putString(TRANSACTION_NUMBER, transaction_number)
                bundle.putString(SKU_NUMBER, sku)

                findNavController().navigate(R.id.cameraFragment, bundle)
            }
        }
        return binding.root
    }
}