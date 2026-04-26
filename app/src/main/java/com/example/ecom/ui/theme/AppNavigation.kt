package com.example.ecom.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecom.pages.CheckOutPage
import com.example.ecom.pages.ProductDetailsPage
import com.example.ecom.pages.categoryProductPage
import com.example.ecom.screen.AuthScreen
import com.example.ecom.screen.LoginScreen
import com.example.ecom.screen.SignupScreen
import com.example.ecom.screen.homeScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@Composable
fun AppNavigation(modifier: Modifier){
    val navController = rememberNavController()
    GlobalNavigation.navController = navController
    val isLoggedIn = Firebase.auth.currentUser!=null
    val firstpage = if(isLoggedIn) "homescreen" else "auth"
    NavHost(navController=navController, startDestination = firstpage) {
        composable("auth"){
            AuthScreen(modifier=Modifier,navController)
        }
        composable("login"){
            LoginScreen(modifier,navController)
        }
        composable("signup"){
            SignupScreen(modifier,navController)
        }
        composable("homescreen"){
            homeScreen(modifier,navController)
        }
        composable("category-product/{categoryId}"){
            var categoryId = it.arguments?.getString("categoryId")
            categoryProductPage(modifier,categoryId?:"")
        }
        composable("product-details/{ProductId}"){
            var ProductId = it.arguments?.getString("ProductId")
            ProductDetailsPage(modifier,ProductId?:"")
        }
        composable("checkout"){
            CheckOutPage(modifier)
        }
    }
}

object GlobalNavigation{
    lateinit var navController: NavHostController
}