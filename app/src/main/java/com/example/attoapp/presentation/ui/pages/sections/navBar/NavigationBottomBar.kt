package com.example.attoapp.presentation.ui.pages.sections.navBar

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.attoapp.presentation.navigation.Routes
import com.example.attoapp.presentation.viewmodel.DataViewModel
import com.example.attoapp.ui.theme.AccentColor
import com.example.attoapp.ui.theme.OsnTextColor

data class BarItem(
    val id:Int,
    val icon: ImageVector ,
    val title : String,
    val rout: String,
)

val barItemsList = listOf(
    BarItem(
        id = 0,
        icon = Icons.Rounded.Home,
        title = "Главная",
        rout = Routes.MAIN,
    ),
    BarItem(
        id = 1,
        icon = Icons.Rounded.Search,
        title = "Каталог",
        rout = Routes.CATALOG
    ),
    BarItem(
        id = 2,
        icon = Icons.Rounded.ShoppingCart,
        title = "Корзина",
        rout = Routes.CART
    ),
    BarItem(
        id = 3,
        icon = Icons.Rounded.FavoriteBorder,
        title = "Избранное",
        rout = Routes.FAVORITE
    ),
    BarItem(
        id = 4,
        icon = Icons.Rounded.AccountBox,
        title = "Профиль",
        rout = Routes.PROFILE
    ),
)

@Composable
fun NavigationBottomBar(navController : NavController, viewModel : DataViewModel) {

    val currentPage = viewModel.currentBottomNavIcon.collectAsState()

    var selected by remember { mutableStateOf(currentPage) }

    LaunchedEffect(currentPage) {
        selected = currentPage
    }

    LazyRow(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally)
    ) {
        items(barItemsList){ item ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(imageVector = item.icon ,contentDescription = "",
                    tint = if(selected.value == item.id) AccentColor else Color.Black ,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            enabled = if(selected.value == item.id) false else true
                        ) {
                            viewModel.updateCurrentNavIcon(item.id)
                            navController.navigate(item.rout)
                        })
                Text(
                    color = if(selected.value == item.id) AccentColor else OsnTextColor ,
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall,
                )
            }

        }
    }
}
