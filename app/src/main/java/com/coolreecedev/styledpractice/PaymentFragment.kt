package com.coolreecedev.styledpractice

import android.content.Context
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
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.data.appointment.AppointmentViewModel
import com.coolreecedev.styledpractice.data.payment.PaymentViewModel
import com.coolreecedev.styledpractice.data.payment.StripeRequest
import com.coolreecedev.styledpractice.databinding.FragmentPaymentBinding
import com.coolreecedev.styledpractice.databinding.FragmentScheduleDateBinding
import com.coolreecedev.styledpractice.util.LOG_TAG
import com.google.gson.GsonBuilder
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.StripeIntent
import kotlinx.android.synthetic.main.fragment_payment.view.*


class PaymentFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var viewModel: PaymentViewModel
    private lateinit var binding: FragmentPaymentBinding
    private val args: PaymentFragmentArgs by navArgs()
    private lateinit var stripe: Stripe




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(PaymentViewModel::class.java)

        binding = FragmentPaymentBinding.inflate(inflater, container, false)

        listener?.onListPaymentFragmentInteraction(true)

        stripe = Stripe(requireContext(), "pk_test_dGcgdptaqW6r4MnnqAZdktZ3")


        with(binding.payButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "Processing Payment")
                Toast.makeText(requireContext(), "Validating Card", Toast.LENGTH_SHORT).show()
                viewModel.createPaymentIntent(StripeRequest(3000, "test@email.com"))
            }
        }

        viewModel.clientSecret.observe(viewLifecycleOwner, Observer {
            Log.i(LOG_TAG, "Confirming Payment")

            val cardInputWidget = binding.cardInputWidget
            cardInputWidget.paymentMethodCreateParams?.let { params ->
                val confirmParams = ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(params, it)
                stripe.confirmPayment(this, confirmParams)
            }
            Log.i(LOG_TAG, "Client: $it")
        })


        return binding.root
    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListPaymentFragmentInteraction(success: Boolean)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        stripe.onPaymentResult(requestCode, data, object : ApiResultCallback<PaymentIntentResult> {
            override fun onSuccess(result: PaymentIntentResult) {
                val paymentIntent = result.intent
                val status = paymentIntent.status
                if (status == StripeIntent.Status.Succeeded) {
                    Log.i(LOG_TAG, "Payment was Successful")
                    Toast.makeText(requireContext(), "Payment was Successful", Toast.LENGTH_SHORT).show()

                } else if (status == StripeIntent.Status.RequiresPaymentMethod) {
                    Toast.makeText(requireContext(), "Payment Failed", Toast.LENGTH_LONG).show()
                    Log.i(LOG_TAG, "Payment Failed w Status: ${status.code}")
                }
            }

            override fun onError(e: Exception) {
                Log.e(LOG_TAG, "Error while processing", e)
                Toast.makeText(requireContext(), "Error Occurred - Try again", Toast.LENGTH_LONG).show()


            }
        })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
