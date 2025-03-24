package com.example.attoapp.presentation.ui.pages.sections.catalogP

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.attoapp.R
import com.example.attoapp.domain.brandsForCatalog
import com.example.attoapp.presentation.viewmodel.DataViewModel
import com.example.attoapp.ui.theme.AccentColor
import com.example.attoapp.ui.theme.OsnTextColor

@Composable
fun AlphabetShops(viewModel : DataViewModel) {

    val brands by viewModel.brands.collectAsState()

    val brandsAlphabet by remember(brands) { mutableStateOf(brandsForCatalog(brands)) }
    LazyColumn(modifier = Modifier
        .fillMaxWidth(0.9f)
        .padding(start = 10.dp ,top = 24.dp)) {
        items(brandsAlphabet) { (letter, brandList) ->
            Text(
                text = letter,
                style = MaterialTheme.typography.labelMedium,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 14.dp, top = 24.dp, bottom = 10.dp),
                color = AccentColor
            )

            brandList.forEach { brand ->
                Row(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth()
                ) {

                    Image(
                        painter = rememberImagePainter(brand.image_url), contentDescription = "Logo of ${brand.name}",
                        modifier = Modifier.size(42.dp)
                    )

                    Column(modifier = Modifier.padding(start = 14.dp)) {
                        Text(
                            text = brand.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = OsnTextColor
                        )
                        Text(
                            text = brand.category,
                            style = MaterialTheme.typography.labelMedium,
                            color = OsnTextColor
                        )
                    }
                }
            }
        }
    }
}