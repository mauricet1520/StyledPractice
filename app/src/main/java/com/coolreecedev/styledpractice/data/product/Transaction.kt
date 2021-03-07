package com.coolreecedev.styledpractice.data.product

data class Transaction (
    val transaction_number: String,
    var totalCost: Double,
    var products: MutableList<Product>? = null
)