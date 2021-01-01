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
import com.coolreecedev.styledpractice.databinding.FragmentOccasionBinding
import com.coolreecedev.styledpractice.ui.ZipCodeFragmentDirections
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
        Log.i(LOG_TAG, "occasion: ${args.occasion}")


        with(binding.anniversaryImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: Anniversary")
                val appointment = Appointment(occasion = "Anniversary", zip = args.zipCode)
                val action = OccasionFragmentDirections.actionOccasionFragmentToPricingOptionsFragment(appointment)
                findNavController().navigate(action)
            }
        }

        with(binding.weddingImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: Wedding")
                val appointment = Appointment(occasion = "Wedding",  zip = args.zipCode)
                val action = OccasionFragmentDirections.actionOccasionFragmentToPricingOptionsFragment(appointment)
                findNavController().navigate(action)
            }
        }

        with(binding.cocktailPartImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: CocktailParty")
                val appointment = Appointment(occasion = "CocktailParty",  zip = args.zipCode)
                val action = OccasionFragmentDirections.actionOccasionFragmentToPricingOptionsFragment(appointment)
                findNavController().navigate(action)
            }
        }

        with(binding.companyPartyImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: CompanyParty")
                val appointment = Appointment(occasion = "CompanyParty", zip = args.zipCode)
                val action = OccasionFragmentDirections.actionOccasionFragmentToPricingOptionsFragment(appointment)
                findNavController().navigate(action)
            }
        }

        with(binding.religiousCeremonyImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: ReligiousCeremony")
                val appointment = Appointment(occasion = "ReligiousCeremony", zip = args.zipCode)
                val action = OccasionFragmentDirections.actionOccasionFragmentToPricingOptionsFragment(appointment)
                findNavController().navigate(action)
            }
        }

        with(binding.theaterNightImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: TheaterNight")
                val appointment = Appointment(occasion = "TheaterNight", zip = args.zipCode)
                val action = OccasionFragmentDirections.actionOccasionFragmentToPricingOptionsFragment(appointment)
                findNavController().navigate(action)
            }
        }

        with(binding.birthDayPartyImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: BirthDayParty")
                val appointment = Appointment(occasion = "BirthDayParty")
                val action = OccasionFragmentDirections.actionOccasionFragmentToPricingOptionsFragment(appointment)
                findNavController().navigate(action)
            }
        }

        with(binding.everydayCasualImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: EverydayCasual")
                val appointment = Appointment(occasion = "EverydayCasual", zip = args.zipCode)
                val action = OccasionFragmentDirections.actionOccasionFragmentToPricingOptionsFragment(appointment)
                findNavController().navigate(action)
            }
        }

        with(binding.everydayImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: EverydayWork")
                val appointment = Appointment(occasion = "EverydayWork", zip = args.zipCode)
                val action = OccasionFragmentDirections.actionOccasionFragmentToPricingOptionsFragment(appointment)
                findNavController().navigate(action)
            }
        }

        with(binding.dinnerPartyImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: DinnerParty")
                val appointment = Appointment(occasion = "DinnerParty", zip = args.zipCode)
                val action = OccasionFragmentDirections.actionOccasionFragmentToPricingOptionsFragment(appointment)
                findNavController().navigate(action)
            }
        }

        with(binding.funeralImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: Funeral")
                val appointment = Appointment(occasion = "Funeral", zip = args.zipCode)
                val action = OccasionFragmentDirections.actionOccasionFragmentToPricingOptionsFragment(appointment)
                findNavController().navigate(action)
            }
        }

        with(binding.interviewImageButton) {
            setOnClickListener {
                Log.i(LOG_TAG, "occasion: Interview")
                val appointment = Appointment(occasion = "Interview" ,zip = args.zipCode)
                val action = OccasionFragmentDirections.actionOccasionFragmentToPricingOptionsFragment(appointment)
                findNavController().navigate(action)
            }
        }


        // Inflate the layout for this fragment
        return binding.root
    }

}
