package com.example.attoapp.presentation.ui.pages.sections.catalogP

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val Letters = listOf(
    "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
)
@Preview
@Composable
fun LetterColumn( ) {
    LazyColumn (
        modifier = Modifier
            .padding(end = 10.dp, top = 40.dp)
    ) {
        items(Letters) { item ->
            Box(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .width(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item,
                    fontSize = 14.sp,
                )
            }
        }
    }
}