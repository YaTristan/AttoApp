package com.example.attoapp.presentation.ui.pages.sections.favoriteP

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.attoapp.presentation.navigation.Routes
import com.example.attoapp.presentation.ui.pages.sections.mainP.ItemCard
import com.example.attoapp.presentation.viewmodel.AuthViewmodel
import com.example.attoapp.presentation.viewmodel.DataViewModel


@Composable
fun FavoriteShoes(dataViewmodel : DataViewModel, navController : NavController){

    val userInfo = dataViewmodel.userInfo.collectAsState()
    val favoriteProduct = userInfo.value?.favorite_productsInfo ?: emptyList()

    LazyVerticalGrid(GridCells.Fixed(3), modifier = Modifier.padding(top = 36.dp, start = 16.dp, end = 16.dp)  ) {
        items(favoriteProduct!!) { item ->
            ItemCard(
                image = "${item.image_url}",
                brand = item.brand_name,
                model = item.name,
                price = "${item.price}",
                isFavorite = true,
                toFavorite = {  },
                onClick = {
                    dataViewmodel.setCurrentItemId(item.id)
                    navController.navigate(Routes.ITEM)
                }
            )
        }
    }
}