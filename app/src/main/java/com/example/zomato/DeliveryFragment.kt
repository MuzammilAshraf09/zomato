package com.example.zomato

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class DeliveryFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_delivery, container, false)

        // Initialize the Add to Cart button
        val buttonIds = listOf(
            R.id.btnAddToCart1,
            R.id.btnAddToCart2,
            R.id.btnAddToCart3,
            R.id.btnAddToCart4
        )

        // Set click listeners for each button in a loop
        buttonIds.forEachIndexed { index, buttonId ->
            view.findViewById<Button>(buttonId).setOnClickListener {
                val message = "Item ${index + 1} added to cart!"
                showAddToCartToast(message)
            }
        }

        return view
    }

    // Helper function to display a custom Toast message with animation
    private fun showAddToCartToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).apply {
            view?.alpha = 0f
            view?.animate()?.alpha(1f)?.setDuration(500)?.start()
            show()
        }
    }



}
