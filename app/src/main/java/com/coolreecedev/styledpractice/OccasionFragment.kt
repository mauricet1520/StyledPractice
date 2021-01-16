package com.coolreecedev.styledpractice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.data.Appointment
import com.coolreecedev.styledpractice.data.customer.Customer
import com.coolreecedev.styledpractice.databinding.FragmentOccasionBinding
import com.coolreecedev.styledpractice.util.LOG_TAG

/**
 * A simple [Fragment] subclass.
 */
class OccasionFragment : Fragment() {

    private val args: OccasionFragmentArgs by navArgs()
    private lateinit var binding: FragmentOccasionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOccasionBinding.inflate(inflater, container, false)
        Log.i(LOG_TAG, "zipCode: ${args.zipCode}")
        Log.i(LOG_TAG, "first_name: ${args.customer?.first_name}")
        Log.i(LOG_TAG, "first_name: ${args.customer?.email}")
        Log.i(LOG_TAG, "first_name: ${args.customer?.uid}")

        with(binding.imageView14) {
            setOnClickListener {
                moveOccasionRight()
            }
        }


        with(binding.imageView26) {
            setOnClickListener {
                moveOccasionLeft()
            }
        }



        with(binding.anniversaryImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: Anniversary")
                val appointment =
                    Appointment(
                        occasion = "Anniversary",
                        zip = args.zipCode
                    )
                displayPricingOptionsFragment(appointment = appointment, customer = args.customer!!)
            }
        }

        with(binding.weddingImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: Wedding")
                val appointment =
                    Appointment(
                        occasion = "Wedding",
                        zip = args.zipCode
                    )
                displayPricingOptionsFragment(appointment = appointment, customer = args.customer!!)

            }
        }

        with(binding.cocktailPartImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: CocktailParty")
                val appointment =
                    Appointment(
                        occasion = "CocktailParty",
                        zip = args.zipCode
                    )
                displayPricingOptionsFragment(appointment = appointment, customer = args.customer!!)

            }
        }

        with(binding.companyPartyImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: CompanyParty")
                val appointment =
                    Appointment(
                        occasion = "CompanyParty",
                        zip = args.zipCode
                    )
                displayPricingOptionsFragment(appointment = appointment, customer = args.customer!!)
            }
        }

        with(binding.religiousCeremonyImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: ReligiousCeremony")
                val appointment =
                    Appointment(
                        occasion = "ReligiousCeremony",
                        zip = args.zipCode
                    )
                displayPricingOptionsFragment(appointment = appointment, customer = args.customer!!)

            }
        }

        with(binding.theaterNightImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: TheaterNight")
                val appointment =
                    Appointment(
                        occasion = "TheaterNight",
                        zip = args.zipCode
                    )
                displayPricingOptionsFragment(appointment = appointment, customer = args.customer!!)
            }
        }

        with(binding.birthDayPartyImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: BirthDayParty")
                val appointment =
                    Appointment(
                        occasion = "BirthDayParty"
                    )
                displayPricingOptionsFragment(appointment = appointment, customer = args.customer!!)
            }
        }

        with(binding.everydayCasualImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: EverydayCasual")
                val appointment =
                    Appointment(
                        occasion = "EverydayCasual",
                        zip = args.zipCode
                    )
                displayPricingOptionsFragment(appointment = appointment, customer = args.customer!!)

            }
        }

        with(binding.everydayImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: EverydayWork")
                val appointment =
                    Appointment(
                        occasion = "EverydayWork",
                        zip = args.zipCode
                    )
                displayPricingOptionsFragment(appointment = appointment, customer = args.customer!!)

            }
        }

        with(binding.dinnerPartyImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: DinnerParty")
                val appointment =
                    Appointment(
                        occasion = "DinnerParty",
                        zip = args.zipCode
                    )
                displayPricingOptionsFragment(appointment = appointment, customer = args.customer!!)

            }
        }

        with(binding.funeralImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: Funeral")
                val appointment =
                    Appointment(
                        occasion = "Funeral",
                        zip = args.zipCode
                    )
                displayPricingOptionsFragment(appointment = appointment, customer = args.customer!!)

            }
        }

        with(binding.interviewImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: Interview")
                val appointment =
                    Appointment(
                        occasion = "Interview",
                        zip = args.zipCode
                    )
                displayPricingOptionsFragment(appointment = appointment, customer = args.customer!!)

            }
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun moveOccasionRight() {
        with(binding.horizontalScrollView) {
            this.pageScroll(HorizontalScrollView.FOCUS_RIGHT)
        }
    }

    private fun moveOccasionLeft() {
        with(binding.horizontalScrollView) {
            this.pageScroll(HorizontalScrollView.FOCUS_LEFT)
        }
    }

    private fun ImageButton.displayPricingOptionsFragment(
        appointment: Appointment,
        customer: Customer
    ) {
        val action =
            OccasionFragmentDirections.actionOccasionFragmentToPricingOptionsFragment(
                appointment,
                customer
            )
        findNavController().navigate(action)
    }

}
