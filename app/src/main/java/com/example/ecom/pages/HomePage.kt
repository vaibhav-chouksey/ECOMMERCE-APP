package com.example.ecom.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecom.components.BannerView
import com.example.ecom.components.CategoriesViews
import com.example.ecom.components.HeaderView

@Composable
fun HomePage(modifier: Modifier=Modifier){
    Column(modifier = Modifier.fillMaxSize().padding(18.dp)) {
        Spacer(modifier=Modifier.height(16.dp))
        HeaderView(modifier = Modifier)
        Spacer(modifier=Modifier.height(10.dp))
        BannerView(modifier=Modifier.height(150.dp))
        Text(text="Categories",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        )
        CategoriesViews(modifier)
    }

}