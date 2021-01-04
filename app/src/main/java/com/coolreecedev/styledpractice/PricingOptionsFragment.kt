package com.coolreecedev.styledpractice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.data.Appointment
import com.coolreecedev.styledpractice.databinding.FragmentPricingOptionsBinding
import com.coolreecedev.styledpractice.util.LOG_TAG

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PricingOptionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PricingOptionsFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private val args: PricingOptionsFragmentArgs by navArgs()
    private lateinit var binding: FragmentPricingOptionsBinding

    private var param1: String? = null
    private var param2: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentPricingOptionsBinding.inflate(inflater, container, false)
        Log.i(LOG_TAG, "occasion: ${args.appointment?.occasion}")

        with(binding.virtualStyleButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "setmore_service_name: VirtualStyle")
                it.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
                val appointment =
                    Appointment(
                        occasion = args.appointment?.occasion,
                        zip = args.appointment?.zip,
                        setmore_service_key = "s0f27750942522cbbc953aa6ea01daf169f4c5d4c",
                        setmore_service_name = "Virtual Service"
                    )

                val action =
                    PricingOptionsFragmentDirections.actionPricingOptionsFragmentToScheduleDateFragment(
                        appointment
                    )
                findNavController().navigate(action)
            }
        }

        with(binding.shopClosetButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "setmore_service_name: Shop Your Closet")
                it.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
                val appointment =
                    Appointment(
                        occasion = args.appointment?.occasion,
                        zip = args.appointment?.zip,
                        setmore_service_key = "s735b953ea6365d8a320e45fd13ec1dc2bd6a0401",
                        setmore_service_name = "Shop Your Closet"
                    )

                val action =
                    PricingOptionsFragmentDirections.actionPricingOptionsFragmentToScheduleDateFragment(
                        appointment
                    )
                findNavController().navigate(action)
            }


            with(binding.singleStyleButton) {
                setOnClickListener {
                    Log.i(LOG_TAG, "setmore_service_name: Shop & Style")
                    it.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
                    val appointment =
                        Appointment(
                            occasion = args.appointment?.occasion,
                            zip = args.appointment?.zip,
                            setmore_service_key = "s5c2c44e31f92d7086b35e71316c72a1454778402",
                            setmore_service_name = "Shop & Style"
                        )

                    val action =
                        PricingOptionsFragmentDirections.actionPricingOptionsFragmentToScheduleDateFragment(
                            appointment
                        )
                    findNavController().navigate(action)
                }
            }

            with(binding.styleSubscriptionButton) {
                setOnClickListener {
                    Log.i(LOG_TAG, "setmore_service_name: Style Subscription")
                    it.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
                    val appointment =
                        Appointment(
                            occasion = args.appointment?.occasion,
                            zip = args.appointment?.zip,
                            setmore_service_key = "s293a092281466b5127666d4c50be75bbd17a49f1",
                            setmore_service_name = "Seasonal Style"
                        )

                    val action =
                        PricingOptionsFragmentDirections.actionPricingOptionsFragmentToScheduleDateFragment(
                            appointment
                        )
                    findNavController().navigate(action)
                }
            }
            return binding.root
        }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment PricingOptionsFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            PricingOptionsFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
    }
}