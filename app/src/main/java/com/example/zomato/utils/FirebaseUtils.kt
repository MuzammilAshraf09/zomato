package com.example.zomato.utils

import com.example.zomato.model.DeliveryItem
import com.google.firebase.database.FirebaseDatabase

object FirebaseUtils {

    private val database = FirebaseDatabase.getInstance()
    private val deliveryItemsRef = database.getReference("deliveryItems")

    // Function to add a delivery item to Firebase Realtime Database
    fun addDeliveryItem(item: DeliveryItem) {
        // Create a unique ID for the delivery item by using push() which generates a new ID automatically
        val newItemRef = deliveryItemsRef.push()

        // Set the values for this new delivery item
        newItemRef.setValue(item)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Item added successfully
                    println("Delivery item added successfully")
                } else {
                    // Failed to add item
                    println("Error adding delivery item: ${task.exception?.message}")
                }
            }
    }
}
