package com.example.attoapp.presentation.ui.pages.sections.shopFeedbacks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import coil.compose.rememberImagePainter
import com.example.attoapp.R
import com.example.attoapp.domain.reviewsAll
import com.example.attoapp.presentation.viewmodel.DataViewModel
import com.example.attoapp.ui.theme.OsnTextColor
import com.example.attoapp.ui.theme.StarsBack

@Composable
fun AllFeedbacks(
    viewModel : DataViewModel
) {
    val reviews by viewModel.reviews.collectAsState()
    val reviewsList = reviewsAll(0, reviews)

    LazyColumn(
        modifier = Modifier
            .padding(top = 16.dp, start = 24.dp, end = 24.dp,)
            .fillMaxSize(),

        ) {
        items(reviewsList) { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(painter = painterResource(R.drawable.ava), contentDescription = "Avatar",
                        modifier = Modifier.size(44.dp))

                    Column(
                        modifier = Modifier.padding(start = 12.dp) ,
                    ) {
                        Text(
                            text = "${item.user_name} ${item.user_surname}" ,
                            style = MaterialTheme.typography.titleLarge ,
                            color = OsnTextColor
                        )
                        Text(
                            modifier = Modifier.alpha(0.5f) ,
                            text = item.created_at ,
                            style = MaterialTheme.typography.titleMedium ,
                            color = OsnTextColor
                        )
                    }
                }

            Row(modifier = Modifier.padding(top = 16.dp ,bottom = 8.dp)) {
                repeat(item.stars) {
                    Icon(
                        imageVector = Icons.Rounded.Star ,contentDescription = "Stars" ,
                        tint = Color(0xffF6953A)
                    )
                }
                repeat(5 - item.stars) {
                    Icon(
                        imageVector = Icons.Rounded.Star ,contentDescription = "StarsBack" ,
                        tint = StarsBack
                    )
                }
            }

            if (item.review_text != null) {

                Text(
                    modifier = Modifier.alpha(0.8f) ,
                    text = item.review_text ?: "",
                    style = MaterialTheme.typography.titleMedium ,
                    color = OsnTextColor
                )

            }

            item.photo_urls?.let {
                LazyRow(
                    modifier = Modifier.padding(top = 16.dp ,bottom = 16.dp)
                ) {
                    items(item.photo_urls) { photo ->
                        Box(
                            modifier = Modifier
                                .padding(end = 16.dp)
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