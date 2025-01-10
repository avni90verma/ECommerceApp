package com.example.ecommerceapp.mvvm

import android.util.Log
import com.example.ecommerceapp.api.ApiService
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.model.ProductList
import javax.inject.Inject

class ProductRepository @Inject constructor(private val apiService: ApiService) {



    suspend fun getProducts():List<Product>{
        return apiService.getProducts()
    }



}