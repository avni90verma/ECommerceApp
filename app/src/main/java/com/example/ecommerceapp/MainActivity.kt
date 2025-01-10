package com.example.ecommerceapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil3.compose.rememberAsyncImagePainter
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.model.ProductList
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
        var modifierPadding = Modifier.padding(paddingValues)
        val productList by viewModel.product.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()

       if(isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ){
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Black
                )
            }

       }
        else{
           LazyVerticalGrid(
               columns = GridCells.Adaptive(150.dp),
               modifier = Modifier.padding(paddingValues),
               content = {
                   items(productList.size){ index:Int->
                       ProductCardUI(productList[index])
                   }
               } )
       }
    }
}

@Composable
fun ProductCardUI(product: Product) {
    var ctx = LocalContext.current
    Card(modifier = Modifier
        .height(290.dp)
        .width(212.dp).padding(6.dp)
        .clickable {
            Toast.makeText(ctx, product.title, Toast.LENGTH_SHORT).show() },
        colors = CardDefaults.cardColors(
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp,end = 15.dp,top = 20.dp)
        ) {
            Image(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .width(100.dp)
                    .height(100.dp),
                painter = rememberAsyncImagePainter(model = product.image),
                contentScale = ContentScale.Crop,
                contentDescription = ""
            )

            Text(
                text = product.title,
                modifier = Modifier.padding(top = 28.dp),
                fontFamily = FontFamily.Serif,
                fontSize = 15.sp,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = product.description,
                modifier = Modifier.padding(top = 28.dp),
                fontFamily = FontFamily.Serif,
                fontSize = 15.sp,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "${product.price}",
                    modifier = Modifier.padding(top = 3.dp),
                    fontFamily = FontFamily.Serif,
                    fontSize = 18.sp,
                    color = Color.Black,
                )
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(shape = CircleShape)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_shopping_cart_24),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
            }

        }


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



