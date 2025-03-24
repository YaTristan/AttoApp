package com.example.attoapp.presentation.ui.pages.sections.favoriteP

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.attoapp.data.Brands
import com.example.attoapp.presentation.navigation.Routes
import com.example.attoapp.presentation.navigation.Search
import com.example.attoapp.presentation.ui.pages.sections.navBar.NavigationBottomBar
import com.example.attoapp.presentation.viewmodel.DataViewModel
import com.example.attoapp.ui.theme.OsnBackground
import com.example.attoapp.ui.theme.OsnTextColor

@Composable
fun AllShops(
    navController : NavController,
    viewModel: DataViewModel
) {

    val user = viewModel.userInfo.collectAsState()
    val brands = user.value!!.favorite_shopInfo

    Scaffold(
        topBar = {
            Box (
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .padding(WindowInsets.systemBars.asPaddingValues())
                    .height(32.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    
                ) {
                    Icon(imageVector = Icons.Rounded.PlayArrow, contentDescription = "",
                        modifier = Modifier
                            .size(28.dp)
                            .fillMaxSize()
                            .clickable {
                                navController.navigate(Routes.ALLFAVORITESHOPS)
                            },
                    )
                    
                    Text(
                        text = "Магазины",
                        style = MaterialTheme.typography.titleLarge,
                        color = OsnTextColor
                    )
                }
            }
        },
        bottomBar = { NavigationBottomBar(navController, viewModel = viewModel) }
    ) { paddingValues ->
        LazyVerticalGrid(
            GridCells.Fixed(4),
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxSize()
                .background(OsnBackground)
                .padding(paddingValues)
        ) {

            items(brands!!) { item ->

                Column {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 10.dp ,vertical = 8.dp)
                            .size(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Image(
                            rememberImagePainter(data = item.image_url) ,
                            contentDescription = "Ava" ,
                            contentScale = ContentScale.Fit
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 10.dp ,vertical = 4.dp)
                            .fillMaxWidth(),
                        text = item.name ,
                        style = MaterialTheme.typography.labelMedium ,
                        color = OsnTextColor,
                        textAlign = TextAlign.Center
                    )
                }

            }
        }
    }
}