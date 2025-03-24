package com.example.attoapp.presentation.ui.pages.sections.mainP

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.attoapp.R

data class CategoryData(
    val id:Int,
    val icon:Int,
    val description: String,
)

val categoriesList = listOf(
    CategoryData(
        id = 0,
        icon = R.drawable.category1,
        description = "Категория 1"
    ),
    CategoryData(
        id = 1,
        icon = R.drawable.category2,
        description = "Категория 2"
    ),
    CategoryData(
        id = 2,
        icon = R.drawable.category3,
        description = "Категория 3"
    ),
    CategoryData(
        id = 3,
        icon = R.drawable.category4,
        description = "Категория 4"
    ),
    CategoryData(
        id = 4,
        icon = R.drawable.category5,
        description = "Категория 5"
    ),
)

@Composable
@Preview
fun CategorySection( ) {

    LazyRow {
        items(categoriesList) { item ->
            CategoryBox(onClick = { /*TODO*/ }, icon = item.icon, description = item.description)
        }
    }
}

@Composable
fun CategoryBox( onClick :() -> Unit, icon:Int, description : String) {

    Box(
        modifier = Modifier
            .padding(start = 8.dp)
            .size(85.dp)
            .clickable { onClick() }
    ) {
        Image(painter = painterResource(id = icon), contentDescription = description,
            modifier = Modifier.fillMaxSize())
    }

}