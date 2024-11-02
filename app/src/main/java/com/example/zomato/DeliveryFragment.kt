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
        val btnAddToCart: Button = view.findViewById(R.id.btnAddToCart)

        // Set a click listener for the button
        btnAddToCart.setOnClickListener {
            Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show()
        }

        return view
    }



}
