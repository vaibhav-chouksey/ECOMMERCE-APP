package com.example.ecom.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecom.ViewModel.Apputil
import com.example.ecom.model.ProductModel
import com.example.ecom.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun CheckOutPage(modifier: Modifier = Modifier){
    val userModel = remember { mutableStateOf(UserModel()) }
    val productList = remember { mutableStateListOf<ProductModel>() }
    val subTotal = remember { mutableStateOf(0f) }
    val discount = remember {
        mutableFloatStateOf(0f)
    }

    val tax = remember {
        mutableFloatStateOf(0f)
    }

    val total = remember {
        mutableFloatStateOf(0f)
    }

    fun calculateSubtotal(){
        productList.forEach{
            if(it.actualPrice.isNotEmpty()){
                val qty =userModel.value.cartItems[it.id] ?: 0
                subTotal.value += it.actualPrice.toFloat()*qty
            }
        }
    }
    discount.value = subTotal.value * (Apputil.getDiscountPercent() / 100)
    tax.value = subTotal.value * (Apputil.getTaxPercent() / 100)
    total.value = "%.2f".format(subTotal.value - discount.value + tax.value).toFloat()


    LaunchedEffect(key1 = Unit) {
        // Fetch user data
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(UserModel::class.java)
                    if (result != null) {
                        userModel.value = result

                        // Fetch products based on cart item IDs
                        Firebase.firestore.collection("data")
                            .document("stock")
                            .collection("products")
                            .whereIn("id", userModel.value.cartItems.keys.toList())
                            .get()
                            .addOnCompleteListener { Task ->
                                if (Task.isSuccessful) {
                                    val resultProducts = Task.result.toObjects(ProductModel::class.java)

                                    productList.addAll(resultProducts)
                                    calculateSubtotal()
                                }
                            }
                    }

            }}}

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Checkout",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Deliver to:", fontWeight = FontWeight.SemiBold)
        Text(text = userModel.value.address)
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()


        Spacer(modifier = Modifier.height(16.dp))
        RowCheckoutItems(title = "Subtotal", value = subTotal.value.toString())

        Spacer(modifier = Modifier.height(8.dp))
        RowCheckoutItems(title = "Discount (-)", value = discount.value.toString())

        Spacer(modifier = Modifier.height(8.dp))
        RowCheckoutItems(title = "Tax (+)", value = tax.value.toString())
        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider()
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "To Pay",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "₹" + total.value.toString(),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {Apputil.startPayment(total.value)},
            modifier=Modifier.fillMaxWidth()
                .height(50.dp)) {
            Text("Pay Now")
        }
    }

}
@Composable
fun RowCheckoutItems(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "₹$value",
            fontSize = 18.sp
        )
    }
}
