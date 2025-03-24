package com.example.attoapp.presentation.ui.pages.sections.moreInfoShopP

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.attoapp.R
import com.example.attoapp.data.Brands
import com.example.attoapp.domain.searchListOfBrand
import com.example.attoapp.presentation.navigation.Routes
import com.example.attoapp.presentation.ui.pages.sections.mainP.ItemCard
import com.example.attoapp.presentation.viewmodel.DataViewModel
import com.example.attoapp.ui.theme.AccentColor
import com.example.attoapp.ui.theme.OsnTextColor


@Composable
fun ShopInfoSection( brand : Brands, viewModel : DataViewModel, navController : NavController ) {

    val productsAll = viewModel.productsWithBrands.collectAsState()
    val itemsList = searchListOfBrand(brand.id, productsAll.value)
    var isExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowLeft, contentDescription = "Back",
            modifier = Modifier
                .size(28.dp)
                .fillMaxSize()
                .clickable {
                    navController.popBackStack()
                }
        )
        Icon(imageVector = Icons.Rounded.FavoriteBorder, contentDescription = "Favorite",
            modifier = Modifier
                .size(28.dp)
                .fillMaxSize())
    }

    Column(modifier = Modifier
        .padding(WindowInsets.systemBars.asPaddingValues())
        .padding(horizontal = 16.dp))
    {


        Image(painter = rememberImagePainter(brand.image_url) , contentDescription = "Logo of shop",
            modifier = Modifier
                .padding(top = 36.dp)
                .size(90.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )

        LazyColumn {
          item {
              Text(
                  modifier = Modifier
                      .padding(top = 24.dp ,bottom = 8.dp)
                      .fillMaxWidth(),
                  text = brand.name,
                  style = MaterialTheme.typography.labelLarge,
                  fontSize = 28.sp,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Center
              )
          }
           item {
               Text(
                   modifier = Modifier,
                   text = brand.description ,
                   style = MaterialTheme.typography.bodyMedium ,
                   color = OsnTextColor ,
                   maxLines = if (isExpanded) Int.MAX_VALUE else 3 ,
                   overflow = TextOverflow.Ellipsis
               )
               if(true) {
                   Text(
                       text = if (isExpanded) "Свернуть" else "Развернуть" ,
                       modifier = Modifier.padding(top = 12.dp, bottom = 40.dp).clickable { isExpanded = !isExpanded }
                           .fillMaxWidth() ,
                       textAlign = TextAlign.Center ,
                       style = MaterialTheme.typography.titleMedium ,
                       color = AccentColor
                   )
               }
           }
        }
        LazyVerticalGrid(GridCells.Fixed(3)  ) {
            items(itemsList) { item ->
                ItemCard(
                    image = item.image_url,
                    brand = item.brand_name,
                    model = item.name,
                    price = "${item.price}",
                    isFavorite = true,
                    toFavorite = { },
                    onClick = {
                        viewModel.setCurrentItemId(item.id)
                        navController.navigate(Routes.ITEM)
                    }
                )
            }
        }
    }
}