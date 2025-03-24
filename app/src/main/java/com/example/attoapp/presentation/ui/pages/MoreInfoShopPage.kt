package com.example.attoapp.presentation.ui.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.attoapp.presentation.ui.pages.sections.moreInfoShopP.ShopInfoSection
import com.example.attoapp.presentation.viewmodel.DataViewModel


@Composable
fun MoreInfoShopPage(
    navController : NavController,
    viewModel : DataViewModel
) {
    val brandId = viewModel.currentBrand.collectAsState()
    val brand = viewModel.brands.collectAsState()
    val currentBrand = brand.value[brandId.value!!]
    ShopInfoSection(currentBrand, viewModel, navController )
}