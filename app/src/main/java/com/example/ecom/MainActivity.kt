package com.example.ecom

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold

import androidx.compose.ui.Modifier

import com.example.ecom.ViewModel.Apputil
import com.example.ecom.ui.theme.AppNavigation
import com.example.ecom.ui.theme.EcomTheme
import com.example.ecom.ui.theme.GlobalNavigation
import com.razorpay.PaymentResultListener

class MainActivity : ComponentActivity(),PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onPaymentSuccess(p0: String?) {

        Apputil.clearCartAndAddToOrders()

       val builder = AlertDialog.Builder(this)
        builder.setTitle("Payment Successfull")
            .setMessage("Thank You ! Your payment is completed and your order has been placed")
            .setPositiveButton("OK"){_,_->
                val navController = GlobalNavigation.navController
                navController.popBackStack()
                navController.navigate("homescreen")
            }
            .setCancelable(false)
            .show()

    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Apputil.showToast(this,"Payment Failed")

    }
}
