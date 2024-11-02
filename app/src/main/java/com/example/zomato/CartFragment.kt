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
import com.example.zomato.model.CartItem

class CartFragment : Fragment() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var cartQuote: TextView
    private lateinit var placeOrderButton: Button
    private lateinit var cartAdapter: CartAdapter
    private val cartItems = mutableListOf(
        CartItem("Pizza Margherita", 8.99),
        CartItem("Cheeseburger", 5.49),
        CartItem("Pasta Carbonara", 7.99),
        CartItem("Chocolate Brownie", 3.99),
        CartItem("Caesar Salad", 6.99)
    )

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

        // Set a quote
        cartQuote.text = "Life is uncertain. Eat dessert first!"

        // Setup RecyclerView with Grid Layout
        cartAdapter = CartAdapter(cartItems) { item ->
            cartAdapter.removeItem(item) // Remove item via the adapter
            Toast.makeText(requireContext(), "${item.name} removed", Toast.LENGTH_SHORT).show()
        }
        cartRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        cartRecyclerView.adapter = cartAdapter

        // Set click listener for Place Order button
        placeOrderButton.setOnClickListener {
            Toast.makeText(requireContext(), "Your Order has been placed successfully!", Toast.LENGTH_SHORT).show()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
