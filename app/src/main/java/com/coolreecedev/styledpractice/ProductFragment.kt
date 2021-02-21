package com.coolreecedev.styledpractice

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.coolreecedev.styledpractice.data.product.Product
import kotlinx.android.synthetic.main.fragment_account_settings.view.*
import kotlinx.android.synthetic.main.fragment_products_list.view.*

/**
 * A fragment representing a list of Items.
 */
class ProductFragment : Fragment() {

    private var columnCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_products_list, container, false)

        // Set the adapter
        val recyclerView = view.list

            with(recyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> {
                        val divider = DividerItemDecoration(context, LinearLayoutManager(context).orientation)
                        setHasFixedSize(true)
                        addItemDecoration(divider)
                        LinearLayoutManager(context)
                    }
                    else -> GridLayoutManager(context, columnCount)
                }

                adapter = MyItemRecyclerViewAdapter(listOf(Product(
                    date = "10-31-23", firebase_appointment_id = "1111",
                    name = "Dress",
                    sku_number = "11111111", cost = 13.00.toLong(), item_type = "Blue w")
                ))
            }

        view.addItemText.setOnClickListener {
            findNavController().navigate(R.id.checkoutFragment)
        }
        return view
    }

}