package com.example.attoapp.presentation.ui.pages.sections.cartP

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attoapp.presentation.viewmodel.DataViewModel
import com.example.attoapp.ui.theme.AccentColor
import com.example.attoapp.ui.theme.OsnTextColor

@Composable
fun BottomBuyBar(
    sum : Double,
    count : Int,
    viewmodel : DataViewModel,
){
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp ,vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = sum.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp,
                color = OsnTextColor,
            )
            Text(
                text = "${count} товара",
                style = MaterialTheme.typography.titleMedium,
                color = OsnTextColor,
                modifier = Modifier.alpha(0.6f)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { }
                .background(AccentColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Оформить заказ",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )
        }
    }
}