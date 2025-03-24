package com.example.attoapp.presentation.ui.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.attoapp.presentation.navigation.Search
import com.example.attoapp.presentation.ui.pages.sections.itemP.StructureSizes
import com.example.attoapp.presentation.ui.pages.sections.navBar.NavigationBottomBar
import com.example.attoapp.presentation.viewmodel.DataViewModel
import com.example.attoapp.ui.theme.OsnBackground

@Composable
fun ProfilePage(navController : NavController, viewModel : DataViewModel) {

    Scaffold(
        topBar = { ProfileTopBar(viewModel) },
        bottomBar = { NavigationBottomBar(navController, viewModel = viewModel) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(OsnBackground)
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            StructureSizes(isTop = true, title = "Мои заказы", onClick = {})
            StructureSizes(isTop = false, title = "Мои отзывы", onClick = {})
            Spacer(modifier = Modifier.height(21.dp))
            StructureSizes(isTop = true, title = "Уведомления", onClick = {})
            StructureSizes(isTop = false, title = "Настройки", onClick = {})
            Spacer(modifier = Modifier.height(21.dp))
            StructureSizes(isTop = true, title = "О программе", onClick = {}) // Исправил дублирование "Уведомления"
            Spacer(modifier = Modifier.height(21.dp))
            StructureSizes(isTop = true, title = "Справка", onClick = {})
            StructureSizes(isTop = false, title = "Связаться с нами", onClick = {})
        }
    }

}

@Composable
fun ProfileTopBar(viewModel : DataViewModel) {
    val userInfo = viewModel.userInfo.collectAsState()
    userInfo.value?.let {
        Box(
            modifier = Modifier
                .fillMaxWidth() // Ограничиваем по ширине
                .background(Color(0xffFF881A))
                .padding(WindowInsets.systemBars.asPaddingValues()),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 20.dp ,top = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(data = it.image_url) ,
                    contentDescription = "Image",
                    modifier = Modifier.size(48.dp).fillMaxSize()
                )

                Column(modifier = Modifier.padding(start = 14.dp)) {
                    Text(
                        text = "${it.name} ${it.surname}" ,
                        style = MaterialTheme.typography.titleLarge ,
                        color = Color.White
                    )
                    Text(
                        text = it.number ?: "" ,
                        style = MaterialTheme.typography.bodySmall ,
                        color = Color.White
                    )
                }
            }
        }
    }
}