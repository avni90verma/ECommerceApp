package com.example.ecommerceapp.api

import com.example.ecommerceapp.Constants
import com.example.ecommerceapp.Constants.Companion.List_of_product
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.model.ProductList
import retrofit2.http.GET

interface ApiService {

    @GET(Constants.Companion.List_of_product)
    suspend fun getProducts(): List<Product>
}