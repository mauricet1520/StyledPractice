package com.coolreecedev.styledpractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.coolreecedev.styledpractice.data.Appointment
import com.coolreecedev.styledpractice.data.customer.Customer
import com.coolreecedev.styledpractice.data.customer.CustomerViewModel
import com.coolreecedev.styledpractice.data.product.Product
import com.coolreecedev.styledpractice.data.product.ProductViewModel
import com.coolreecedev.styledpractice.databinding.FragmentCheckoutBinding
import com.coolreecedev.styledpractice.databinding.FragmentProductsBinding
import kotlinx.android.synthetic.main.fragment_account_settings.view.*
import kotlinx.android.synthetic.main.fragment_products_list.view.*
import java.util.*

/**
 * A fragment representing a list of Items.
 */
private const val TRANSACTION_NUMBER = "transaction_number"
private const val APPOINTMENT = "appointment"
private const val CUSTOMER = "customer"
class ProductFragment : Fragment() {

    private var columnCount = 1
    private var appointment: Appointment? = null
    private var customer: Customer? = null
    private var transaction_number: String? = null
    private val args: ProductFragmentArgs by navArgs()
    private lateinit var binding: FragmentProductsBinding
    private lateinit var productViewModel: ProductViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            transaction_number = it.getString(TRANSACTION_NUMBER)
            appointment = it.getParcelable(APPOINTMENT)
            customer = it.getParcelable(CUSTOMER)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_products_list, container, false)
     productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        // Set the adapter
        productViewModel.getProductsInTransaction("12345")

        productViewModel.transactionData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            if (it.products != null) {
                val recyclerView = view.list

                with(recyclerView) {
                    layoutManager = when {
                        columnCount <= 1 -> {
                            val divider =
                                DividerItemDecoration(
                                    context,
                                    LinearLayoutManager(context).orientation
                                )
                            setHasFixedSize(true)
                            addItemDecoration(divider)
                            LinearLayoutManager(context)
                        }
                        else -> GridLayoutManager(context, columnCount)
                    }
                    adapter = MyItemRecyclerViewAdapter(
                        it.products!!
                    )
                }
            }

        })

        view.addItemText.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("appointment", appointment)
            bundle.putParcelable("customer", customer)

            bundle.putString("transaction_number", transaction_number)
            findNavController().navigate(R.id.checkoutFragment, bundle)
        }
        return view
    }

}