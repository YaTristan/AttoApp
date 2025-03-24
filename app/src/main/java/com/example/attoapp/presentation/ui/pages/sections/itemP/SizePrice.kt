package com.example.attoapp.presentation.ui.pages.sections.itemP

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attoapp.presentation.viewmodel.DataViewModel
import com.example.attoapp.ui.theme.AccentColor
import com.example.attoapp.ui.theme.OsnTextColor
import com.example.attoapp.ui.theme.mulishBlack

@Composable
fun SizePrice(price:Double, sizes:List<Int>) {

    var isSelected by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .padding(start = 24.dp, top = 12.dp, end = 24.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = "Размер",
            style = MaterialTheme.typography.titleLarge,
            color = OsnTextColor
        )

        LazyRow(
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            items(sizes) { item ->
                SizeBox(
                    size = item,
                    onClick = {
                        isSelected = item
                    },
                    selected = isSelected
                )
            }
        }
        Row(
            modifier = Modifier.padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "${price}",
                fontSize = 28.sp,
                fontFamily = mulishBlack,
                fontWeight = FontWeight.W700,
                color = OsnTextColor
            )
            Text(
                text = "",
                textDecoration = TextDecoration.LineThrough,
                fontSize = 18.sp,
            )
        }
    }
}

@Composable
fun SizeBox(size:Int, onClick: () -> Unit, selected:Int) {
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.White)
            .border(width = 1.dp ,color = if(selected == size) AccentColor else Color.LightGray ,RoundedCornerShape(5.dp))
            .width(40.dp)
            .height(26.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .alpha(0.6f),
            text = "${size}",
            style = MaterialTheme.typography.titleMedium,
            color = if(selected == size) AccentColor else OsnTextColor
        )
    }
}