package com.example.attoapp.presentation.ui.pages.sections.mainP

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.attoapp.R
import com.example.attoapp.data.MergedProductsBrands
import com.example.attoapp.data.Products
import com.example.attoapp.presentation.navigation.Routes
import com.example.attoapp.presentation.viewmodel.DataViewModel
import com.example.attoapp.ui.theme.AccentColor
import com.example.attoapp.ui.theme.OsnTextColor

@Composable
fun RecommendationSection(
    navController : NavController,
    title:String,
    backgroundColor:Color,
    onClick: (Int) -> Unit,
    viewModel: DataViewModel,
    products: List<MergedProductsBrands>,
) {

    Column(
        modifier = Modifier
            .height(256.dp)
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(start = 8.dp ,top = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(end = 8.dp ,bottom = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(

            ) {
                Text(text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = OsnTextColor
                    )
            }
            Box (
                modifier = Modifier
                    .size(20.dp)
                    .clickable {

                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Rounded.PlayArrow, contentDescription = "MoreInfo")
            }
        }


        LazyRow {
            items(products) { item ->
                ItemCard(
                    image = item.image_url,
                    brand = "${item.brand_name}",
                    model = item.name,
                    price = "${item.price}",
                    isFavorite = item.isFavorite,
                    toFavorite = {
                        viewModel.toggleFavoriteForRecommend(item.id)
                     },
                    onClick = {
                        viewModel.setCurrentItemId(item.id)
                        navController.navigate(Routes.ITEM)
                    }
                )
            }
        }
    }
}

@Composable
fun ItemCard(
    image: String,
    brand: String,
    model: String,
    price: String,
    isFavorite: Boolean,
    toFavorite: () -> Unit,
    onClick : () -> Unit,
) {
    var favorited by remember { mutableStateOf(isFavorite) }

    // Синхронизируем favorited с isFavorite
    LaunchedEffect(isFavorite) {
        favorited = isFavorite
    }

    Box(
        modifier = Modifier
            .padding(end = 4.dp ,bottom = 16.dp)
            .width(120.dp)
            .height(190.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White)
            .border(width = 1.dp ,color = Color.LightGray ,RoundedCornerShape(6.dp))
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Image(
                    painter = rememberImagePainter(image),
                    contentDescription = "Shoe",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                modifier = Modifier.padding(top = 4.dp, start = 6.dp, end = 6.dp),
                text = brand,
                style = MaterialTheme.typography.titleSmall,
                color = OsnTextColor,
            )
            Text(
                modifier = Modifier
                    .padding(top = 2.dp ,start = 6.dp ,end = 6.dp)
                    .alpha(0.6f),
                text = model,
                style = MaterialTheme.typography.labelSmall,
                color = OsnTextColor,
            )
            Box(modifier = Modifier.fillMaxSize().padding(bottom = 6.dp),
                contentAlignment = Alignment.BottomStart,) {
                Text(
                    modifier = Modifier.padding(top = 8.dp ,start = 6.dp ,end = 6.dp,) ,
                    text = price ,
                    style = MaterialTheme.typography.labelMedium,
                    color = OsnTextColor
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(top = 8.dp ,end = 8.dp)
                .size(24.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .clickable {
                    // Изменяем состояние favorited
                    toFavorite()
                    favorited = !favorited
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.FavoriteBorder,
                contentDescription = "favorite",
                tint = if (!favorited) Color.LightGray else Color.Red
            )
        }
    }
}