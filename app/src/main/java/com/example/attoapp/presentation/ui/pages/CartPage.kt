package com.example.attoapp.presentation.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.attoapp.presentation.navigation.Search
import com.example.attoapp.presentation.ui.pages.sections.cartP.BottomBuyBar
import com.example.attoapp.presentation.ui.pages.sections.cartP.CartItemBox
import com.example.attoapp.presentation.ui.pages.sections.cartP.CartTopBar
import com.example.attoapp.presentation.ui.pages.sections.navBar.NavigationBottomBar
import com.example.attoapp.presentation.viewmodel.DataViewModel
import com.example.attoapp.ui.theme.OsnBackground

@Composable
fun CartPage(navController : NavController, viewModel : DataViewModel){

    val cartItems = viewModel.userInfo.collectAsState().value?.cartInfo ?: emptyList()
    val sum = viewModel.sumOfItems.collectAsState().value ?: 0.0
    val count = viewModel.countOfItems.collectAsState().value ?: 0

    Scaffold(
        topBar = { CartTopBar(
            count,
            viewModel = viewModel
        ) },
        bottomBar = {
            Column {
                BottomBuyBar(sum = sum ,count = count, viewModel)
                NavigationBottomBar(navController, viewModel = viewModel)
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(OsnBackground)
                .padding(paddingValues)
        ) {

            items(cartItems!!.size) { index ->
                val item = cartItems[index]
                CartItemBox(
                    image_url = item.product.image_url ,
                    brand = item.product.brand_name,
                    name = item.product.name,
                    color = item.color,
                    size =  item.size.toString(),
                    price = item.product.price.toString(),
                    isSelected = item.isSelected ,
                    isFavorite = item.isFavorite,
                    count = item.count,
                    countMin = { viewModel.updateCount(index, item.count - 1) },
                    countPlus = { viewModel.updateCount(index, item.count + 1) },
                    favorite = { viewModel.toggleFavorite(index) },
                    select = { viewModel.toggleSelection(index) },
                )
            }
        }
    }
}