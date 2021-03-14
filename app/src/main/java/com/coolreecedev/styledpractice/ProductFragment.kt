package com.coolreecedev.styledpractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
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
import kotlinx.android.synthetic.main.fragment_products_list.*
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
    private  var totalCost: Double = 0.0

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


        if (transaction_number != null) {
            productViewModel.getProductsInTransaction(transaction_number!!)
        }

       view.completeTransactionButton.setOnClickListener {
            if (totalCost > 0) {
                val bundle = Bundle()
                bundle.putParcelable("appointment", appointment)
                bundle.putParcelable("customer", customer)

                bundle.putString("transaction_number", transaction_number)
                bundle.putDouble("total_cost", totalCost)
                findNavController().navigate(R.id.checkoutPaymentFragment, bundle)
            }
        }


        productViewModel.transactionData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            if (it != null) {
                totalCost = it.totalCost

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
                    if(it.products != null) {
                        adapter = MyItemRecyclerViewAdapter(
                            it.products!!
                        )
                    }else {
                        adapter = MyItemRecyclerViewAdapter(mutableListOf(Product(name = "EmptyCart")))
                    }
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