package com.example.ecom.model

data class ProductModel(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val actualPrice: String = "",
    val category: String = "",
    val images: List<String> = emptyList(),
    val otherDetail : Map<String,String> = mapOf()
)
