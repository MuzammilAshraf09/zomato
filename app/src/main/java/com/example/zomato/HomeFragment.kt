package com.example.zomato

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {
    // Declare UI components
    private lateinit var menuDrawer: ImageView
    private lateinit var sortText: TextView
    private lateinit var sortSpinner: Spinner
    private lateinit var deliveryText: TextView
    private lateinit var deliverySpinner: Spinner
    private lateinit var ratingText: TextView
    private lateinit var ratingSpinner: Spinner
    private lateinit var typeText: TextView
    private lateinit var typeSpinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize views
        menuDrawer = view.findViewById(R.id.menu_drawer)
        sortText = view.findViewById(R.id.sortText)
        sortSpinner = view.findViewById(R.id.sortSpinner)
        deliveryText = view.findViewById(R.id.deliveryText)
        deliverySpinner = view.findViewById(R.id.deliverySpinner)
        ratingText = view.findViewById(R.id.ratingText)
        ratingSpinner = view.findViewById(R.id.ratingSpinner)
        typeText = view.findViewById(R.id.typeText)
        typeSpinner = view.findViewById(R.id.typeSpinner)

        // Drawer menu click: Communicate with HomeActivity to open the drawer
        menuDrawer.setOnClickListener {
            (activity as? HomeActivity)?.openDrawer()  // Access HomeActivity's method
        }

        // Set OnClickListeners for the text views to toggle spinner visibility
        sortText.setOnClickListener {
            sortSpinner.visibility = if (sortSpinner.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        deliveryText.setOnClickListener {
            deliverySpinner.visibility = if (deliverySpinner.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        ratingText.setOnClickListener {
            ratingSpinner.visibility = if (ratingSpinner.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        typeText.setOnClickListener {
            typeSpinner.visibility = if (typeSpinner.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        // Set OnClickListeners for grid items
        val gridLayout: ViewGroup = view.findViewById(R.id.gridLayout)
        for (i in 0 until gridLayout.childCount) {
            val card = gridLayout.getChildAt(i)
            card.setOnClickListener {
                showItemDetailsDialog(card)
            }
        }

        return view
    }

    // Function to show item details dialog
    private fun showItemDetailsDialog(card: View) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_item_details, null)

        // Get the UI elements from the dialog view
        val itemName: TextView = dialogView.findViewById(R.id.dialog_item_name)
        val itemPrice: TextView = dialogView.findViewById(R.id.dialog_item_price)
        val itemImage: ImageView = dialogView.findViewById(R.id.dialog_item_image)
        val addToCartButton: Button = dialogView.findViewById(R.id.dialog_add_to_cart)

        // Set the item details based on which card was clicked
        when (card.id) {
            R.id.card_trending -> {
                itemName.text = "Trending"
                itemPrice.text = "$10"  // Hardcoded price
                itemImage.setImageResource(R.drawable.trending_image)  // Replace with actual image resource
            }
            R.id.card_pure_veg -> {
                itemName.text = "Pure Veg"
                itemPrice.text = "$8"
                itemImage.setImageResource(R.drawable.pure_veg_image)
            }
            R.id.card_family_dining -> {
                itemName.text = "Family Dining"
                itemPrice.text = "$12"
                itemImage.setImageResource(R.drawable.family_dining_image)
            }
            R.id.card_premium_dining -> {
                itemName.text = "Premium Dining"
                itemPrice.text = "$15"
                itemImage.setImageResource(R.drawable.premium_dining_image)
            }
            R.id.card_buffet -> {
                itemName.text = "Buffet"
                itemPrice.text = "$20"
                itemImage.setImageResource(R.drawable.buffet_image)
            }
            R.id.card_drink_dine -> {
                itemName.text = "Drink and Dine"
                itemPrice.text = "$18"
                itemImage.setImageResource(R.drawable.drink_dine_image)
            }
        }

        // Create and show the dialog
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(dialogView)
        val dialog = dialogBuilder.create()

        // Add a listener to the button
        addToCartButton.setOnClickListener {
            Toast.makeText(requireContext(), "${itemName.text} added to cart", Toast.LENGTH_SHORT).show()
            dialog.dismiss()  // Close the dialog
        }

        dialog.show()  // Show the dialog
    }
}
