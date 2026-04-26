package com.example.ecom.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ecom.R

@Composable
fun AuthScreen(modifier: Modifier, navController: NavHostController){
    Column(modifier=Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.shoping),
            contentDescription = "Shoping",
            modifier=Modifier.fillMaxWidth().height(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Start Your Shopping Journey Now", fontSize = 32.sp, )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Best Ecom Platform")

        Button(onClick = {navController.navigate("login")}) {
            Text("Login")
        }

        Button(onClick = {navController.navigate("signup")}){
            Text("Sign up")
        }

    }

}