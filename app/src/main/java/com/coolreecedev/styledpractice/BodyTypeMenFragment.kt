package com.coolreecedev.styledpractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.coolreecedev.styledpractice.databinding.FragmentBodyTypeBinding
import com.coolreecedev.styledpractice.databinding.FragmentBodyTypeMenBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BodyTypeMenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BodyTypeMenFragment : Fragment() {

    private lateinit var binding: FragmentBodyTypeMenBinding
    private val args: BodyTypeMenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBodyTypeMenBinding.inflate(inflater, container, false)

        with(binding.bodyTypeMenNextButton) {
            setOnClickListener {
                if (args.customer?.body_type?.isNotEmpty()!!) {
                    val action =
                        BodyTypeMenFragmentDirections.actionBodyTypeMenFragmentToMenPickClothingFragment(
                            appointment = args.appointment,
                            customer = args.customer
                        )
                    findNavController().navigate(action)
                }else {
                    Toast.makeText(context, "Please Select BodyType", Toast.LENGTH_SHORT).show()
                }
            }

        }


        with(binding.muscularId) {
            saveBodyType("Muscular")
        }

        with(binding.averageId) {
            saveBodyType("Average")
        }

        with(binding.huskyId) {
            saveBodyType("Husky")
        }

        with(binding.slimId) {
            saveBodyType("Slim")
        }

        return binding.root
    }


    private fun ImageButton.saveBodyType(type: String) {
        setOnClickListener {
            args.customer?.body_type?.add(type)
            background = resources.getDrawable(R.color.colorAccent)
        }
    }

}