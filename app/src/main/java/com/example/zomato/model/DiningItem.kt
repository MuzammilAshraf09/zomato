package com.example.zomato.model

data class DiningItem(
    val id: Int = 0,
    val name: String = "",
    val dishName: String = "",
    val restaurantName: String = "",
    val price: Double = 0.0,
    val deliveryInfo: String = "",
    val discountInfo: String = "",
    val imageResId: Int = 0 // Local drawable resource ID
)
