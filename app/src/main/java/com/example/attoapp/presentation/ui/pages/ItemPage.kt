package com.example.attoapp.presentation.ui.pages

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.attoapp.domain.reviewsAll
import com.example.attoapp.domain.searchListOfBrand
import com.example.attoapp.presentation.navigation.Routes
import com.example.attoapp.presentation.navigation.Search
import com.example.attoapp.presentation.ui.pages.sections.itemP.BottomButtonBar
import com.example.attoapp.presentation.ui.pages.sections.itemP.FeedBack
import com.example.attoapp.presentation.ui.pages.sections.itemP.ItemImDesc
import com.example.attoapp.presentation.ui.pages.sections.itemP.SizePrice
import com.example.attoapp.presentation.ui.pages.sections.itemP.StructureSizes
import com.example.attoapp.presentation.ui.pages.sections.mainP.RecommendationSection
import com.example.attoapp.presentation.ui.pages.sections.navBar.NavigationBottomBar
import com.example.attoapp.presentation.viewmodel.DataViewModel
import com.example.attoapp.ui.theme.OsnBackground


@Composable
fun ItemPage( navController : NavController, viewModel : DataViewModel) {

    val productsWithBrands = viewModel.productsWithBrands.collectAsState().value
    val product = productsWithBrands.firstOrNull { it.id == viewModel.currentItem.value }!!

    val brand_logo =
        viewModel.brands.collectAsState().value.firstOrNull { it.id == product.brand_id }!!.image_url

    val productsAll = viewModel.productsWithBrands.collectAsState()
    val itemsList = searchListOfBrand(product.brand_id ,productsAll.value)

    val reviews by viewModel.reviews.collectAsState()
    val reviewsCount = reviewsAll(product.id ,reviews).size


    var favorited by remember { mutableStateOf(product?.isFavorite ?: false) }

    LaunchedEffect(product?.isFavorite) {
        favorited = product?.isFavorite ?: false
    }

    Scaffold(
        topBar = {
            Row(
            modifier = Modifier
                .padding(WindowInsets.systemBars.asPaddingValues())
                .padding(horizontal = 8.dp)
                .fillMaxWidth() ,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowLeft ,contentDescription = "Back" ,
                modifier = Modifier
                    .size(36.dp)
                    .fillMaxSize()
                    .clickable { navController.popBackStack() }
            )
            Icon(
                imageVector = Icons.Rounded.FavoriteBorder ,
                contentDescription = "Favorite" ,
                tint = if (favorited) Color.Red else Color.LightGray ,
                modifier = Modifier
                    .size(32.dp)
                    .fillMaxSize()
                    .clickable {
                        viewModel.toggleFavoriteForRecommend(product!!.id)
                        favorited = !favorited
                        Log.d("favorite" ,"${product.isFavorite}")
                    }
            )
            } },
        bottomBar = { BottomButtonBar() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(OsnBackground)
                .padding(paddingValues)
        ) {
            item {
                ItemImDesc(
                    product.image_url ,
                    product.brand_name ,
                    product.name ,
                    brand_logo ,
                    product.description
                )
            }
            item { SizePrice(product.price ,product.sizes) }
            item { StructureSizes(isTop = true ,title = "Состав" ,onClick = {}) }
            item { StructureSizes(isTop = false ,title = "Размеры" ,onClick = {}) }
            item { FeedBack(navController ,review = product.review ,reviewsCount = reviewsCount) }
            item {
                RecommendationSection(
                    navController ,
                    title = "Похожие товары" ,
                    backgroundColor = Color.White ,
                    onClick = { itemId ->
                        viewModel.setCurrentItemId(itemId);
                        navController.navigate(Routes.ITEM)
                    } ,
                    products = itemsList ,
                    viewModel = viewModel ,
                )
            }
        }
    }
}
