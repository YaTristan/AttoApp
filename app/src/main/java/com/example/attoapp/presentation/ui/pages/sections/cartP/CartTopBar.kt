package com.example.attoapp.presentation.ui.pages.sections.cartP

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.attoapp.presentation.viewmodel.DataViewModel
import com.example.attoapp.ui.theme.OsnTextColor
import com.example.attoapp.ui.theme.StarsBack

@Composable
fun CartTopBar(
    cartItemSize : Int,
    viewModel : DataViewModel,
) {
    val allSelected = viewModel.allSelected.collectAsState()
    var checked by remember { mutableStateOf(allSelected.value ?: true ) }

    Column(
        modifier = Modifier
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${cartItemSize} товара в корзине",
                style = MaterialTheme.typography.titleLarge,
                color = OsnTextColor
            )
        }

        Row(
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Text(
                modifier = Modifier.alpha(0.6f),
                text = "Удалить выбранные",
                style = MaterialTheme.typography.titleMedium,
                color = OsnTextColor
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .alpha(0.6f),
                text = "Выбрать все",
                style = MaterialTheme.typography.titleMedium,
                color = OsnTextColor
            )
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(StarsBack)
                    .clickable {
                        viewModel.toggleAllSelection()
                    },
                contentAlignment = Alignment.Center
            ) {
                if (checked == true) Icon(imageVector = Icons.Rounded.Done, contentDescription = "")
            }

        }
        Divider(
            modifier = Modifier
                .padding(vertical = 6.dp)
                .height(1.dp)
                .fillMaxWidth()
                .alpha(0.1f),
            color = OsnTextColor,
        )
    }

    checked = allSelected.value ?: true
}