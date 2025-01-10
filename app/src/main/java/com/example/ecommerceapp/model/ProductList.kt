package com.example.ecommerceapp.model

data class ProductList(
    val status: String,
    val products: List<Product>,
    val message: String
)