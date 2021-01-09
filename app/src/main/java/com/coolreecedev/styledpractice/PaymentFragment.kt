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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.data.Appointment
import com.coolreecedev.styledpractice.data.appointment.AppointmentViewModel
import com.coolreecedev.styledpractice.data.appointment.CreateAppointmentRequest
import com.coolreecedev.styledpractice.data.appointment.CreateCustomerRequest
import com.coolreecedev.styledpractice.data.appointment.StyledCustomerAppointmentRequest
import com.coolreecedev.styledpractice.data.payment.PaymentViewModel
import com.coolreecedev.styledpractice.data.payment.StripeRequest
import com.coolreecedev.styledpractice.databinding.FragmentPaymentBinding
import com.coolreecedev.styledpractice.util.LOG_TAG
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.StripeIntent


class PaymentFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var viewModel: PaymentViewModel
    private lateinit var appointmentViewModel: AppointmentViewModel
    private lateinit var binding: FragmentPaymentBinding
    private val args: PaymentFragmentArgs by navArgs()
    private lateinit var stripe: Stripe




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(PaymentViewModel::class.java)
        appointmentViewModel = ViewModelProviders.of(this).get(AppointmentViewModel::class.java)

        binding = FragmentPaymentBinding.inflate(inflater, container, false)

        listener?.onListPaymentFragmentInteraction(true)

        stripe = Stripe(requireContext(), "pk_test_08OvXs4KcpfiPblGJxlrTWIl")

        Log.i(LOG_TAG, "PaymentFragment: customer Id: ${args.customer?.uid}")
        Log.i(LOG_TAG, "PaymentFragment: customer firstName: ${args.customer?.first_name}")

        with(binding.cardInputWidget) {
            clearFocus()
        }

        with(binding.payButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "Processing Payment")
                Toast.makeText(requireContext(), "Validating Card", Toast.LENGTH_SHORT).show()
                viewModel.createPaymentIntent(StripeRequest(3000, "test@email.com"))
            }
        }



        appointmentViewModel.appointmentData.observe(viewLifecycleOwner, Observer {
            val customer = args.customer

            val appointment = args.appointment

            appointment?.appointment_id = it.appointment_id
            appointment?.setmore_appointment_key = it.setmore_appointment_key
            appointment?.setmore_customer_key = it.setmore_customer_key
            appointment?.setmore_label = it.setmore_label
            appointment?.setmore_service_key = it.setmore_service_key
            appointment?.setmore_staff_key = it.setmore_staff_key
            appointment?.start_time = it.start_time
            appointment?.end_time = it.end_time
            appointment?.street_address = it.street_address
            appointment?.city = it.city
            appointment?.state = it.state
            appointment?.stylist_id = it.setmore_staff_key

            Log.i(LOG_TAG, "Appointment Id ${it.appointment_id}")
            customer?.appointment_ids = arrayListOf()
            customer?.appointment_ids?.add(it.appointment_id!!)
            customer?.setmore_customer_id = it.setmore_customer_key

            val action =
                PaymentFragmentDirections.actionPaymentFragmentToClothesTypeFragment(
                    appointment = appointment,
                    customer = customer
                )
            findNavController().navigate(action)

        })


        viewModel.clientSecret.observe(viewLifecycleOwner, Observer {
            Log.i(LOG_TAG, "Confirming Payment")

            val cardInputWidget = binding.cardInputWidget
            cardInputWidget.paymentMethodCreateParams?.let { params ->
                val confirmParams =
                    ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(params, it)
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
                    Toast.makeText(requireContext(), "Payment was Successful", Toast.LENGTH_SHORT)
                        .show()
                    appointmentViewModel.createAppointment(
                        StyledCustomerAppointmentRequest(
                            createCustomerRequest = CreateCustomerRequest(
                                first_name = args.customer?.first_name,
                                email_id = args.customer?.email,
                                address = args.customer?.address,
                                postal_code = args.customer?.zip,
                                city = args.customer?.city,
                                state = args.customer?.state
                            ),
                            createAppointmentRequest = CreateAppointmentRequest(
                                staff_key = args.appointment?.setmore_staff_key,
                                service_key = args.appointment?.setmore_service_key,
                                start_time = args.appointment?.start_time,
                                end_time = args.appointment?.end_time,
                                label = "creating a appointment"
                            )
                        )

                    )

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


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
