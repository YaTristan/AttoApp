package com.example.attoapp.presentation.ui.pages.sections.mainP

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.attoapp.R
import com.example.attoapp.ui.theme.OsnTextColor
import kotlin.math.absoluteValue

data class Card(
    val id:Int,
    val image : Int,
)

val cards = listOf(
    Card(
        id = 0,
        image = R.drawable.card,
    ),
    Card(
        id = 1,
        image = R.drawable.card,
    ),
    Card(
        id = 2,
        image = R.drawable.card,
    ),
)
@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun CardSection() {
    val pagerState = rememberPagerState { cards.size }

    Column(modifier = Modifier.padding(top = 16.dp)) {

        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            contentPadding = PaddingValues(horizontal = 6.dp)
        ) { page ->
            val pageOffset =
                (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
            val scale = 1f - (0.15f * pageOffset.absoluteValue)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                BoxForCard(image = cards[page].image)
            }
        }

        Row(
            modifier = Modifier
                .padding(top = 12.dp, bottom = 32.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(cards.size) { index ->
                val isSelected = index == pagerState.currentPage
                Icon(
                    imageVector = Icons.Rounded.AddCircle,
                    contentDescription = "",
                    tint = if (isSelected) OsnTextColor else Color.LightGray,
                    modifier = Modifier.size(8.dp)
                )
            }
        }
    }
}



@Composable
fun BoxForCard(image: Int){
    Image(
        painter = painterResource(id = image) ,contentDescription = "card",
        modifier = Modifier
            .width(320.dp)
            .height(181.dp),
        contentScale = ContentScale.Fit
        )
}