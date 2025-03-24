package com.example.attoapp.presentation.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.attoapp.presentation.navigation.Search
import com.example.attoapp.presentation.ui.pages.sections.catalogP.AlphabetShops
import com.example.attoapp.presentation.ui.pages.sections.catalogP.LetterColumn
import com.example.attoapp.presentation.ui.pages.sections.mainP.CardSection
import com.example.attoapp.presentation.ui.pages.sections.mainP.CategorySection
import com.example.attoapp.presentation.ui.pages.sections.mainP.RecommendationSection
import com.example.attoapp.presentation.ui.pages.sections.navBar.NavigationBottomBar
import com.example.attoapp.presentation.viewmodel.DataViewModel
import com.example.attoapp.ui.theme.OsnBackground


@Composable
fun CatalogPage( navController : NavController, viewModel : DataViewModel ) {
    Scaffold(
        topBar = { Search() },
        bottomBar = { NavigationBottomBar(navController, viewModel = viewModel) }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(OsnBackground)
                .padding(paddingValues)
        ) {
            AlphabetShops(viewModel = viewModel)
            LetterColumn()
        }
    }
}