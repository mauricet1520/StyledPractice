package com.coolreecedev.styledpractice.data.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.coolreecedev.styledpractice.data.appointment.AppointmentRepository

class ProductViewModel(app: Application): AndroidViewModel(app)  {
    private val productRepository = ProductRepository(app)
    val transactionData = productRepository.transactionData

    fun getProductsInTransaction(transaction_number: String) {
        productRepository.getProductsInTransaction(transaction_number)
    }

    fun saveProductInTransaction(transaction: Transaction) {
        productRepository.saveProductInTransaction(transaction)
    }
}