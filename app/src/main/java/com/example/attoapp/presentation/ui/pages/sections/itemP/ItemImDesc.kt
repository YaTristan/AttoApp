package com.example.attoapp.presentation.ui.pages.sections.itemP

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.attoapp.R
import com.example.attoapp.ui.theme.AccentColor
import com.example.attoapp.ui.theme.OsnTextColor
import com.example.attoapp.ui.theme.SubTextColor

@Composable
fun ItemImDesc(
    image_url: String,
    brand_name:String,
    name:String,
    brand_log:String,
    description:String
) {
    
    var isExpanded by remember { mutableStateOf(false) }
    val lineCount = description.count { it == '\n' } + 1

    Column {

        Image(
            painter = rememberImagePainter(image_url) , contentDescription = "shoe",
            modifier = Modifier
                .height(407.dp)
                .fillMaxWidth()
            )

        Row(
           modifier = Modifier
               .padding(top = 20.dp ,start = 24.dp)
               .height(50.dp)
               .width(200.dp)
        ) {
            Image(
                painter = rememberImagePainter(brand_log), contentDescription = "logo",
                modifier = Modifier.size(50.dp)
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text(
                        text = brand_name,
                        style = MaterialTheme.typography.titleLarge,
                        color = OsnTextColor,
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = "Rate",
                            tint = Color(0xffF6953A),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            modifier = Modifier.padding(start = 6.dp),
                            text = "4.8",
                            style = MaterialTheme.typography.labelMedium,
                            color = OsnTextColor
                        )
                        Text(
                            modifier = Modifier.padding(start = 6.dp),
                            text = "1767 Отзывов",
                            style = MaterialTheme.typography.labelMedium,
                            color = SubTextColor
                        )
                    }
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(start = 24.dp,top =24.dp, end = 24.dp),
            text = name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = OsnTextColor
        )
        Text(
            modifier = Modifier
                .padding(start = 24.dp ,top = 12.dp ,end = 24.dp)
                .alpha(0.6f),
            text = description ,
            style = MaterialTheme.typography.bodyMedium ,
            color = OsnTextColor ,
            maxLines = if (isExpanded) Int.MAX_VALUE else 3 ,
            overflow = TextOverflow.Ellipsis
        )
        if(true) {
            Text(
                text = if (isExpanded) "Свернуть" else "Развернуть" ,
                modifier = Modifier.padding(top = 12.dp).clickable { isExpanded = !isExpanded }
                    .fillMaxWidth() ,
                textAlign = TextAlign.Center ,
                style = MaterialTheme.typography.titleMedium ,
                color = AccentColor
            )
        }
    }
}