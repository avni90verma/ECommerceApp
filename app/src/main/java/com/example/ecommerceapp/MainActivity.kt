package com.example.ecommerceapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.mvvm.ProductViewModel
import com.example.ecommerceapp.ui.theme.ECommerceAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.time.measureTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel : ProductViewModel by viewModels()
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ECommerceAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    HomePageUI(viewModel)
                }
            }
        }
    }
}

@Composable
private fun HomePageUI(viewModel: ProductViewModel) {
    Scaffold(
        topBar = {
            SimpleLightTopAppBar("API Listing")
        }
    ) { paddingValues ->
        val productList by viewModel.product.collectAsState()

        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            content = {
                items(productList) { product ->
                    Text(text = product.title)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleLightTopAppBar(title:String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge
            )
        },

        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Red
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue)
    )

}



