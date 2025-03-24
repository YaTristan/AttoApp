package com.example.attoapp.presentation.ui.pages.sections.itemP

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.attoapp.ui.theme.OsnTextColor

@Composable
fun StructureSizes(isTop: Boolean, title:String, onClick : () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .height(45.dp)
            .clickable { onClick() }
            .clip(
                if (isTop) RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 8.dp
                ) else RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
            )
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                if (isTop) RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 8.dp
                ) else RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),

            ),
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = if (isTop) 12.dp else 8.dp,
                    bottom = if (!isTop) 12.dp else 8.dp
                )
                .fillMaxSize(),
           horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontSize = 16.sp,
                color = OsnTextColor
            )

            Icon(imageVector = Icons.Rounded.Done, contentDescription = "More Info",
                modifier = Modifier
                    .size(10.dp)
                    .fillMaxSize())
        }
    }
}
