package com.example.attoapp.presentation.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.attoapp.presentation.navigation.Search
import com.example.attoapp.presentation.ui.pages.sections.catalogP.AlphabetShops
import com.example.attoapp.presentation.ui.pages.sections.catalogP.LetterColumn
import com.example.attoapp.presentation.ui.pages.sections.favoriteP.FavoriteShoes
import com.example.attoapp.presentation.ui.pages.sections.favoriteP.ShopsFavoriteRow
import com.example.attoapp.presentation.ui.pages.sections.navBar.NavigationBottomBar
import com.example.attoapp.presentation.viewmodel.AuthViewmodel
import com.example.attoapp.presentation.viewmodel.DataViewModel
import com.example.attoapp.ui.theme.OsnBackground


@Composable
fun FavoritePage( navController : NavController, authViewmodel : AuthViewmodel, dataViewModel : DataViewModel ) {

    dataViewModel.fetchUserInfo()

    Scaffold(
        topBar = { Search() },
        bottomBar = { NavigationBottomBar(navController, viewModel = dataViewModel) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(OsnBackground)
                .padding(paddingValues)
        ) {
            ShopsFavoriteRow(navController,dataViewModel)
            FavoriteShoes(dataViewModel, navController)
        }
    }
}