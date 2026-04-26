package com.example.ecom.components

import android.hardware.biometrics.BiometricManager.Strings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Preview
@Composable
fun BannerView(modifier: Modifier = Modifier){
    var bannerList by remember { mutableStateOf<List<String>>(emptyList())
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data")
            .document("banners")
            .get().addOnCompleteListener(){
                bannerList = it.result.get("urls") as List<String>
            }
    }

    Column {
        val pagerState= rememberPagerState(0){
            bannerList.size
        }

        HorizontalPager(
            state = pagerState,
            pageSpacing = 24.dp
        ) {
            AsyncImage(
                model = bannerList.get(it),
                contentDescription = "banner image",
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))

            )
        }}

            Spacer(modifier = Modifier.height(16.dp))

//            DotsIndicator(
//                dotCount = bannerList.size,
//                type = ShiftIndicatorType(
//                    DotGraphic(
//                        color = MaterialTheme.colorScheme.primary
//                    )
//                ),
//                pagerState = pagerState
//            )





}