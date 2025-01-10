package com.example.ecommerceapp.mvvm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.model.ProductList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {


    private val _product = MutableStateFlow(emptyList<Product>())
    val product: StateFlow<List<Product>> = _product.asStateFlow()

    val isLoading = MutableStateFlow(false)

    init{
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val result = repository.getProducts()
                _product.value = result
            }
            catch (e:Exception){
                Log.e("TAG","Error in fetchProducts:${e.message}")
            }
            finally {
                isLoading.value = false
            }
        }
    }
}