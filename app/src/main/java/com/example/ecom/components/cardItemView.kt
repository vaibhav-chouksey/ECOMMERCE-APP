package com.example.ecom.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment


import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ecom.ViewModel.Apputil
import com.example.ecom.model.ProductModel
import com.example.ecom.ui.theme.GlobalNavigation
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import okhttp3.internal.notify

@Composable
fun CartItemViews(modifier: Modifier=Modifier,productId:String,qty:Long){

    var product by remember {
        mutableStateOf(ProductModel())
    }
    var context = LocalContext.current
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data")
            .document("stock").collection("products")
            .document(productId).get().addOnCompleteListener{
                if(it.isSuccessful){
                    var result = it.result.toObject(ProductModel::class.java)
                    if(result != null) {
                            product=result
                        }

                }
            }
    }
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()


            ,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(modifier = modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = product.images.firstOrNull(),
                contentDescription = product.title,
                modifier = Modifier
                    .height(110.dp)
                    .width(110.dp)
            )
            Column(
                modifier = modifier.padding(12.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center

            ) {
                Text(

                    text = product.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                        text = "₹" + product.actualPrice,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                   text = "₹" + product.price,
//                    fontSize = 12.sp,
//                   style = TextStyle(textDecoration = TextDecoration.LineThrough)
//               )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                    
                ) {
                    IconButton(onClick = { Apputil.removeFromCart(
                        context = context,
                        productId = productId,
                        removeAll = false
                    )}) {
                        Text(text = "-", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }

                    Text(text = "$qty", fontSize = 16.sp)

                    IconButton(onClick = { Apputil.addToCart(
                        context = context,
                        productId = productId
                    ) }) {
                        Text(text = "+", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }

            }
            IconButton(onClick = {Apputil.removeFromCart(
                context = context,
                productId = productId,
                removeAll = true
            )}){
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }

}

