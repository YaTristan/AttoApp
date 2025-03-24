package com.example.attoapp.presentation.ui.pages.sections.shopFeedbacks

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attoapp.R
import javax.annotation.meta.When

@Preview
@Composable
fun ShopInfoFeedbackSort(){
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
    ){
        Row(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .height(50.dp)
                .width(200.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logoadidas) , contentDescription = "logo",
                modifier = Modifier.size(50.dp)
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(
                        text = "Adidas",
                        fontSize = 16.sp,
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = "Rate",
                            tint = Color(0xffF6953A) ,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            modifier = Modifier.padding(start = 6.dp),
                            text = "4.8",
                            fontSize = 12.sp,
                        )
                        Text(
                            modifier = Modifier.padding(start = 6.dp),
                            text = "1767 Отзывов",
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }

        Row(){
            BoxForSort(type = 0)
            BoxForSort(type = 1)
            BoxForSort(type = 2)
        }
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            BoxForSort(type = 3, stars = 5)
            BoxForSort(type = 3, stars = 4)
        }
        Row {
            BoxForSort(type = 3, stars = 3)
            BoxForSort(type = 3, stars = 2)
            BoxForSort(type = 3, stars = 1)
        }
    }
}

@Composable
fun BoxForSort(type:Int, stars:Int = 0) {

    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(5.dp))
            .height(26.dp)
            .wrapContentWidth()
            .background(Color.White)
            .padding(vertical = 6.dp ,horizontal = 12.dp) ,
        contentAlignment = Alignment.Center
    ) {
        when(type) {

            0 -> {
                Text(
                text = "Все (269)",
                fontSize = 12.sp,)
            }
            1 -> {
                Row {
                    Icon(
                        imageVector = Icons.Rounded.AccountBox ,contentDescription = "Photo" ,
                        modifier = Modifier
                            .size(15.dp)
                            .fillMaxSize()
                    )
                    Text(
                        modifier = Modifier.padding(start = 6.dp) ,
                        text = "(16)" ,
                        fontSize = 12.sp ,
                    )
                }
            }
            2 -> {
                Row {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder ,contentDescription = "Photo" ,
                        modifier = Modifier
                            .size(15.dp)
                            .fillMaxSize()
                    )
                    Text(
                        modifier = Modifier.padding(start = 6.dp) ,
                        text = "(16)" ,
                        fontSize = 12.sp ,
                    )
                }
            }
            3 -> {
                Row() {
                    repeat(stars) {
                        Icon(
                            imageVector = Icons.Rounded.Star, contentDescription = "Stars",
                            tint = Color.Black
                        )
                    }
                    Text(
                        modifier = Modifier.padding(start = 6.dp),
                        text = "(16)" ,
                        fontSize = 12.sp ,
                    )
                }
            }
        }
    }
}