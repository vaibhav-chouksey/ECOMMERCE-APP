package com.example.ecom.ViewModel


import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.example.ecom.model.OrderModel
import com.example.ecom.ui.theme.GlobalNavigation
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.razorpay.Checkout
import org.json.JSONObject
import perfetto.protos.AndroidStartupMetric
import java.util.UUID

object Apputil {
    fun showToast(context: Context,message:String){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }
    fun addToCart(context: Context, productId: String) {
        val userDoc = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)

        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()
                val currentQuantity = currentCart[productId] ?: 0
                val updatedQuantity = currentQuantity + 1

                val updatedCart = mapOf("cartItems.$productId" to updatedQuantity)

                userDoc.update(updatedCart)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showToast(context, "Item added to the cart")
                        } else {
                            showToast(context, "Failed adding item to the cart")
                        }
                    }
            }
        }
    }

    fun removeFromCart(context: Context, productId: String,removeAll:Boolean) {
        val userDoc = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)

        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()
                val currentQuantity = currentCart[productId] ?: 0
                val updatedQuantity = currentQuantity - 1

                val updatedCart =
                    if (updatedQuantity<=0 || removeAll){
                        mapOf("cartItems.$productId" to FieldValue.delete())
                    }else {
                        mapOf("cartItems.$productId" to updatedQuantity)
                    }
                userDoc.update(updatedCart)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showToast(context, "Item removed from the cart")
                        } else {
                            showToast(context, "Failed removing item from the cart")
                        }
                    }
            }
        }
    }
    fun getDiscountPercent():Float{
        return 10.0f
    }
    fun getTaxPercent():Float{
        return 13.0f
    }

    fun razorPayApiKey():String{
        return "rzp_test_V1WWJn1vHbzEyV"
    }
    fun startPayment(amount: Float) {
        val checkout = Checkout()
        checkout.setKeyID(razorPayApiKey())

        val options = JSONObject()
        options.put("name", "Easy Shop")
        options.put("description", "")
        options.put("amount", (amount * 100).toInt()) // Amount in paise
        options.put("currency", "INR") // 🇮🇳 Indian Rupees

        checkout.open(GlobalNavigation.navController.context as Activity,options)
    }
    fun clearCartAndAddToOrders() {
        val userDoc = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)

        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()

                val order = OrderModel(
                    id = "ORD"+UUID.randomUUID().toString().replace("-","").take(10).uppercase(),
                    userId = FirebaseAuth.getInstance().currentUser?.uid!!,
                    date = Timestamp.now(),
                    items = currentCart,
                    status = "ORDERED",
                    address = it.result.get("address") as String
                )

                Firebase.firestore.collection("orders")
                    .document(order.id).set(order)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            userDoc.update("cartItems", FieldValue.delete())
                        }
                    }
            }
        }
    }


}