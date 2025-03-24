package com.example.attoapp.presentation.ui.pages.sections.favoriteP

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.attoapp.R
import com.example.attoapp.presentation.navigation.Routes
import com.example.attoapp.presentation.viewmodel.AuthViewmodel
import com.example.attoapp.presentation.viewmodel.DataViewModel
import com.example.attoapp.ui.theme.OsnTextColor

@Composable
fun ShopsFavoriteRow(
    navController : NavController,
    dataViewmodel: DataViewModel
) {

    val userInfo = dataViewmodel.userInfo.collectAsState()
    val favoriteShop = userInfo.value?.favorite_shopInfo ?: emptyList()

    if (favoriteShop.isEmpty()) {

        Text("Нет данных", fontSize = 16.sp)
    } else {
        Column(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 6.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Магазины",
                    style = MaterialTheme.typography.titleLarge,
                    color = OsnTextColor
                )

                Icon(imageVector = Icons.Rounded.PlayArrow, contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                        .fillMaxSize()
                        .clickable {
                            navController.navigate(Routes.ALLFAVORITESHOPS)
                        }
                )
            }

            LazyRow(
                modifier = Modifier.padding(top = 16.dp)
            ) {
                items(favoriteShop) { item ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(painter = rememberImagePainter(item.image_url), contentDescription = "logo",
                            modifier = Modifier
                                .clickable {
                                    dataViewmodel.setCurrentBrandId(item.id)
                                    navController.navigate(Routes.SHOPALLINFO)
                                }
                                .size(80.dp)
                                .padding(bottom = 10.dp)
                        )
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.labelMedium,
                            color = OsnTextColor
                        )
                    }
                }
            }
        }
    }
}
