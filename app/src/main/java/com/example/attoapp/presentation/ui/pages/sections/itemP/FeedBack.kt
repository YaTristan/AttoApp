package com.example.attoapp.presentation.ui.pages.sections.itemP

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.attoapp.R
import com.example.attoapp.data.Reviews
import com.example.attoapp.presentation.navigation.Routes
import com.example.attoapp.presentation.viewmodel.DataViewModel
import com.example.attoapp.ui.theme.CountOfFeedbacks
import com.example.attoapp.ui.theme.OsnTextColor
import com.example.attoapp.ui.theme.StarsBack

@Composable
fun FeedBack( navController : NavController, review : Reviews?, reviewsCount : Int) {


    Column(
        modifier = Modifier
            .padding(top = 32.dp ,start = 24.dp ,end = 24.dp)
            .fillMaxSize() ,

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically ,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .clickable { navController.navigate(route = Routes.FEEDBACKS_ITEM) }
        ) {
            Text(
                text = "Отзывы" ,
                style = MaterialTheme.typography.titleLarge ,
                color = OsnTextColor
            )
            Text(
                modifier = Modifier.padding(start = 8.dp) ,
                text =  "${reviewsCount}",
                style = MaterialTheme.typography.titleLarge ,
                color = CountOfFeedbacks
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Rounded.PlayArrow ,
                contentDescription = "Arrow" ,
                modifier = Modifier.size(20.dp).fillMaxSize()
            )
        }

        if (review != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ava) ,contentDescription = "Avatar" ,
                    modifier = Modifier.size(44.dp).fillMaxSize()
                )

                Column(
                    modifier = Modifier.padding(start = 12.dp) ,
                ) {
                    Text(
                        text = "${review.user_name} ${review.user_surname}" ,
                        style = MaterialTheme.typography.titleLarge ,
                        color = OsnTextColor
                    )
                    Text(
                        modifier = Modifier.alpha(0.5f) ,
                        text = review.created_at ,
                        style = MaterialTheme.typography.titleMedium ,
                        color = OsnTextColor
                    )
                }
            }

            Row(modifier = Modifier.padding(top = 16.dp ,bottom = 8.dp)) {
                repeat(review.stars) {
                    Icon(
                        imageVector = Icons.Rounded.Star ,contentDescription = "Stars" ,
                        tint = Color(0xffF6953A)
                    )
                }
                repeat(5 - review.stars) {
                    Icon(
                        imageVector = Icons.Rounded.Star ,contentDescription = "StarsBack" ,
                        tint = StarsBack
                    )
                }
            }
            if (review.review_text != null) {

                Text(
                    modifier = Modifier.alpha(0.8f) ,
                    text = review.review_text ?: "",
                    style = MaterialTheme.typography.titleMedium ,
                    color = OsnTextColor
                )

            }

            if(review.photo_urls != null){
                LazyRow(
                    modifier = Modifier.padding(
                        top = if (review.review_text != "") 16.dp else 0.dp ,
                        bottom = 16.dp,
                    )
                ) {
                    items(review.photo_urls) { photo ->
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .width(156.dp)
                                .height(153.dp)
                                .clip(RoundedCornerShape(5.dp))
                        ) {
                            Image(
                                painter = rememberImagePainter(photo) ,
                                contentDescription = "photo" ,
                                modifier = Modifier.fillMaxSize() ,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }
}