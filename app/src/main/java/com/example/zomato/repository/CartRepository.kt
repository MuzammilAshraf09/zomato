// CartRepository.kt
package com.example.zomato.repository

import androidx.lifecycle.MutableLiveData
import com.example.zomato.model.CartItem

object CartRepository {
    val cartItems = MutableLiveData<MutableList<CartItem>>(mutableListOf())

    // Function to add an item to the cart
    fun addItem(item: CartItem) {
        val currentItems = cartItems.value ?: mutableListOf()
        currentItems.add(item)
        cartItems.value = currentItems // Update the cart with the new item
    }

    // Function to remove an item from the cart
    fun removeItem(item: CartItem) {
        val currentItems = cartItems.value ?: mutableListOf()
        currentItems.remove(item)
        cartItems.value = currentItems // Update the cart after removal
    }
    fun clearCart() {
        cartItems.value = mutableListOf()  // Clear the list of cart items
    }
}
