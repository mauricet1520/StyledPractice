package com.coolreecedev.styledpractice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.coolreecedev.styledpractice.data.Appointment
import com.coolreecedev.styledpractice.data.appointment.CreateAppointmentRequest
import com.coolreecedev.styledpractice.data.appointment.CreateCustomerRequest
import com.coolreecedev.styledpractice.data.appointment.StyledCustomerAppointmentRequest
import com.coolreecedev.styledpractice.data.checkout.CheckoutViewModel
import com.coolreecedev.styledpractice.data.customer.Customer
import com.coolreecedev.styledpractice.data.payment.PaymentViewModel
import com.coolreecedev.styledpractice.data.payment.StripeRequest
import com.coolreecedev.styledpractice.databinding.FragmentCheckoutPaymentBinding
import com.coolreecedev.styledpractice.databinding.FragmentPaymentBinding
import com.coolreecedev.styledpractice.util.LOG_TAG
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.StripeIntent
import kotlinx.android.synthetic.main.fragment_checkout_payment.*
import kotlinx.android.synthetic.main.fragment_checkout_payment.view.*
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.math.withSign


private const val APPOINTMENT = "appointment"
private const val CUSTOMER = "customer"
private const val TRANSACTION_NUMBER = "transaction_number"
private const val TOTAL_COST = "total_cost"

class CheckoutPaymentFragment : Fragment() {

    private lateinit var viewModel: CheckoutViewModel
    private var appointment: Appointment? = null
    private var customer: Customer? = null
    private var transaction_number: String? = null
    private lateinit var stripe: Stripe
    private var total_cost: Double = 0.0
    private lateinit var binding: FragmentCheckoutPaymentBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            appointment = it.getParcelable(APPOINTMENT)
            customer = it.getParcelable(CUSTOMER)
            transaction_number = it.getString(TRANSACTION_NUMBER)
            total_cost = it.getDouble(TOTAL_COST)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(CheckoutViewModel::class.java)
        stripe = Stripe(requireContext(), "pk_test_08OvXs4KcpfiPblGJxlrTWIl")


        binding = FragmentCheckoutPaymentBinding.inflate(inflater, container, false)

        with(binding.cardInputWidgetId) {
            clearFocus()
        }

        val total = "$$total_cost"
        binding.totalCostId.text = total

        binding.payButton.setOnClickListener {
            Log.i(LOG_TAG, "Processing Payment")
            Toast.makeText(requireContext(), "Validating Card", Toast.LENGTH_SHORT).show()
            if (total_cost > 0.0) {

                val cost = total_cost.toBigDecimal().setScale(2)


                Log.i(LOG_TAG, "Total $total_cost")
                Log.i(LOG_TAG, "Cost $cost")

                Log.i(LOG_TAG, "Cost ${cost.toLong()}")


                viewModel.createPaymentIntent(StripeRequest(cost.toLong(), customer?.email!!))
            } else {
                Toast.makeText(requireContext(), "Total Cost is set to $0", Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.clientSecret.observe(viewLifecycleOwner, Observer {
            Log.i(LOG_TAG, "Confirming Payment: $it")

            val cardInputWidget = binding.cardInputWidgetId
            cardInputWidget.paymentMethodCreateParams?.let { params ->
                val confirmParams =
                    ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(params, it)
                stripe.confirmPayment(this, confirmParams)
            }
            Log.i(LOG_TAG, "Client: $it")
        })

        return binding.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        stripe.onPaymentResult(requestCode, data, object : ApiResultCallback<PaymentIntentResult> {
            override fun onSuccess(result: PaymentIntentResult) {
                val paymentIntent = result.intent
                val status = paymentIntent.status
                if (status == StripeIntent.Status.Succeeded) {
                    Log.i(LOG_TAG, "Payment was Successful")
                    Toast.makeText(requireContext(), "Payment was Successful", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.appointmentLookupFragment)


                } else if (status == StripeIntent.Status.RequiresPaymentMethod) {
                    Toast.makeText(requireContext(), "Payment Failed", Toast.LENGTH_LONG).show()
                    Log.i(LOG_TAG, "Payment Failed w Status: ${status.code}")
                }
            }

            override fun onError(e: Exception) {
                Log.e(LOG_TAG, "Error while processing", e)
                Toast.makeText(requireContext(), "Error Occurred - Try again", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }



}