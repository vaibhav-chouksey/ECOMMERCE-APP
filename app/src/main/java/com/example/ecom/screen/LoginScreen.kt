package com.example.ecom.screen

import androidx.compose.runtime.Composable


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.material3.*
import androidx.compose.material3.Text

import com.example.ecom.R
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ecom.ViewModel.Apputil
import com.example.ecom.ViewModel.AuthViewModel


//@Preview
@Composable
fun LoginScreen(modifier: Modifier=Modifier,navController: NavController,authViewModel: AuthViewModel= viewModel()){
    val email  = remember {
        mutableStateOf("")
    }
    val password  = remember {
        mutableStateOf("")
    }
    var isLoading  = remember {
        mutableStateOf(false)
    }

    var context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().padding(8.dp).background(color = Color.White)
        , verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Welcome Back", modifier = Modifier.padding(top = 3.dp), fontSize = 40.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Text("sign in to your account", modifier = Modifier, fontSize = 32.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Image(painterResource(id = R.drawable.shoping) , contentDescription = "Login Image", modifier = Modifier.size(250.dp))
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(value = email.value, onValueChange = {
            email.value = it
        }, label={
            Text("Email Address")
        })

        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(value = password.value, onValueChange = {
            password.value = it
        },visualTransformation = PasswordVisualTransformation(), label={
            Text("Password")
        })
        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            isLoading.value=true
            authViewModel.login(email.value,password.value){ success,errorMessage->
                if(success){
                    isLoading.value = false
                    navController.navigate("homescreen"){
                        //clearing thr stack login n signup on back shouls be there    popUpTo("auth"){inclusive=true}
                    }
                }else{
                    isLoading.value = false
                    Apputil.showToast(context=context, message = "Something Went Wrong")
                }

            }
        },  enabled = !isLoading.value) {
            Text("Log In", fontSize = 18.sp)
        }
//        Spacer(modifier = Modifier.height(10.dp))
//        Text(text = "Forgot Password", modifier = Modifier.clickable {  }, fontSize = 18.sp)
//        Spacer(modifier = Modifier.height(10.dp))
//        Text(text = "Sign up",fontSize = 22.sp)
    }
}