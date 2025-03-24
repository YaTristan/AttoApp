package com.example.attoapp.presentation.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.attoapp.presentation.ui.pages.sections.shopFeedbacks.AllFeedbacks
import com.example.attoapp.presentation.ui.pages.sections.shopFeedbacks.ShopInfoFeedbackSort
import com.example.attoapp.presentation.viewmodel.DataViewModel


@Composable
fun ShopFeedbacks( navController : NavController,viewModel : DataViewModel ){
    Column {
        ShopInfoFeedbackSort()
        AllFeedbacks(viewModel = viewModel)
    }
}