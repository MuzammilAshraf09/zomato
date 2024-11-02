package com.example.zomato

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class DiningFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // No parameters needed for this fragment currently
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dining, container, false)

        // Initialize the Add to Cart button
        val addToCartButton: Button = view.findViewById(R.id.dialog_add_to_cart)

        // Set an onClickListener to show a toast message
        addToCartButton.setOnClickListener {
            Toast.makeText(requireContext(), "Added to Cart", Toast.LENGTH_SHORT).show()
        }

        return view
    }


}
