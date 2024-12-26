package com.example.zomato

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zomato.adapter.CartAdapter
import com.example.zomato.repository.CartRepository
import com.example.zomato.model.CartItem
import com.google.firebase.auth.FirebaseAuth
import android.app.AlertDialog

class CartFragment : Fragment() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var cartQuote: TextView
    private lateinit var placeOrderButton: Button
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        cartRecyclerView = view.findViewById(R.id.cartRecyclerView)
        cartQuote = view.findViewById(R.id.cartQuote)
        placeOrderButton = view.findViewById(R.id.placeOrderButton)

        // Setup RecyclerView
        cartAdapter = CartAdapter(mutableListOf()) { item ->
            // Remove item from the cart
            CartRepository.removeItem(item)
            Toast.makeText(requireContext(), "${item.name} removed", Toast.LENGTH_SHORT).show()
        }
        cartRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        cartRecyclerView.adapter = cartAdapter

        // Observe cart data
        CartRepository.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.updateCartItems(items) // Update adapter when cart data changes

            // Handle empty cart scenario
            if (items.isEmpty()) {
                cartQuote.text = "Your cart is empty! Add some delicious items."
                placeOrderButton.isEnabled = false // Disable the Place Order button
            } else {
                cartQuote.text = "Life is uncertain. Eat dessert first!"
                placeOrderButton.isEnabled = true // Enable the Place Order button
            }
        }

        // Set click listener for Place Order button
        placeOrderButton.setOnClickListener {
            if (cartAdapter.itemCount > 0) {
                // Show the dialog with order details
                val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_order_details, null)
                val dialog = AlertDialog.Builder(requireContext())
                    .setView(dialogView)
                    .create()

                // Set email from Firebase Auth (ensure the user is authenticated)
                val userEmail = FirebaseAuth.getInstance().currentUser?.email ?: "Not logged in"
                dialogView.findViewById<TextView>(R.id.tvUserEmail).text = "Email: $userEmail"

                // Optionally update other fields like address and delivery info
                dialogView.findViewById<TextView>(R.id.tvDeliveryAddress).text = "Delivery Address: 1234 Main St, City, Country"
                dialogView.findViewById<TextView>(R.id.tvDeliveryInfo).text = "Expected Delivery Time: 30-45 minutes"

                // Confirm order button click
                dialogView.findViewById<Button>(R.id.btnConfirmOrder).setOnClickListener {
                    // Show a success message
                    Toast.makeText(requireContext(), "Your Order has been placed successfully!", Toast.LENGTH_SHORT).show()

                    // Clear the cart after successful order
                    CartRepository.clearCart() // Ensure to have a clearCart method in the repository

                    // Dismiss the dialog
                    dialog.dismiss()

                    // Navigate to another screen (HomeFragment in this case)
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment())
                        .addToBackStack(null)
                        .commit()
                }

                // Show the dialog
                dialog.show()
            }
        }
    }
}
