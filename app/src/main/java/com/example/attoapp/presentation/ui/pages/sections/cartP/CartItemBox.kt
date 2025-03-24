package com.example.attoapp.presentation.ui.pages.sections.cartP

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberImagePainter
import com.example.attoapp.ui.theme.OsnTextColor
import com.example.attoapp.ui.theme.StarsBack


@Composable
fun CartItemBox(
    image_url : String,
    brand : String,
    name : String,
    color : String,
    size : String,
    price : String,
    count : Int,
    select : () -> Unit,
    favorite : () -> Unit,
    countMin : () -> Unit,
    countPlus : () ->Unit,
    isSelected : Boolean,
    isFavorite : Boolean,
){
    var selected by remember { mutableStateOf(isSelected) }
    var favorited by remember { mutableStateOf(isFavorite) }
    LaunchedEffect(isSelected) {
        selected = isSelected
    }
    LaunchedEffect(isFavorite) {
        favorited = isFavorite
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(start = 8.dp ,end = 8.dp ,bottom = 24.dp)
            .clip(RoundedCornerShape(6.dp))
            .border(0.48.dp ,color = Color.LightGray ,RoundedCornerShape(6.dp))
    ) {
        Box(
            modifier = Modifier
                .padding(end = 12.dp)
                .fillMaxHeight()
                .width(120.dp)
        ) {
            Image(painter = rememberImagePainter(data = image_url) ,contentDescription = "",
                modifier = Modifier.fillMaxSize())
        }
        Column(
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                Text(
                    text = brand,
                    style = MaterialTheme.typography.titleMedium,
                    color = OsnTextColor
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(24.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(StarsBack)
                        .clickable {
                            selected = !selected
                            select()
                        },
                    contentAlignment = Alignment.Center
                ) {
                 if(selected) { Icon(imageVector =  Icons.Rounded.Done ,contentDescription = "") }
                }
            }
            Text(
                text = name,
                style = MaterialTheme.typography.labelMedium,
                color = OsnTextColor,
                modifier = Modifier.alpha(0.6f)
            )
            Text(
                text = "Цвет: ${color}",
                style = MaterialTheme.typography.labelMedium,
                color = OsnTextColor,
                modifier = Modifier
                    .padding(top = 6.dp)
                    .alpha(0.5f)
            )
            Text(
                text = "Размер: ${size}",
                style = MaterialTheme.typography.labelMedium,
                color = OsnTextColor,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .alpha(0.5f)
            )
            Text(
                text = "${price} Rub",
                style = MaterialTheme.typography.titleMedium,
                color = OsnTextColor,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .height(30.dp)
                        .width(92.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(StarsBack)
                        .padding(4.dp) ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color.White)
                            .clickable { countMin() } ,
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "-" ,
                            color = OsnTextColor ,
                            textAlign = TextAlign.Center
                        )
                    }

                    Box(
                        modifier = Modifier.weight(1f) ,
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${count}" ,
                            color = OsnTextColor
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color.White)
                            .clickable { countPlus() } ,
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+" ,
                            color = OsnTextColor ,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(24.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(StarsBack)
                        .align(Alignment.CenterVertically)
                        .padding(4.dp)
                        .clickable {
                            favorited = !favorited
                            favorite()
                                   },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "favorite",
                        tint = if (!isFavorite) Color.Black else Color.Red
                    )
                }
            }
        }
    }
}