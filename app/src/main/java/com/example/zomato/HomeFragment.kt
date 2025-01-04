package com.example.zomato

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
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
import androidx.navigation.fragment.findNavController
import com.example.zomato.adapter.CartAdapter
import com.example.zomato.repository.CartRepository
import com.example.zomato.model.CartItem
import com.example.zomato.model.DeliveryItem

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
    private lateinit var locationTextView: TextView  // Add a TextView for displaying the location


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        locationTextView = view.findViewById(R.id.userLocationTextView)  // Make sure this ID matches your layout

        val location = arguments?.getString("LOCATION") ?: "Location not set"
        locationTextView.text = location  // Set location to the TextView

        // Drawer menu click to communicate with HomeActivity to open the drawer
        menuDrawer.setOnClickListener {
            (activity as? HomeActivity)?.openDrawer()
        }

        // Toggle spinner visibility on TextView click
        sortText.setOnClickListener {
            sortSpinner.toggleVisibility()
        }
        deliveryText.setOnClickListener {
            deliverySpinner.toggleVisibility()
        }
        ratingText.setOnClickListener {
            ratingSpinner.toggleVisibility()
        }
        typeText.setOnClickListener {
            typeSpinner.toggleVisibility()
        }

        // Set OnClickListeners for grid items
        val gridLayout: ViewGroup = view.findViewById(R.id.gridLayout)
        for (i in 0 until gridLayout.childCount) {
            val card = gridLayout.getChildAt(i)
            card.setOnClickListener {
                showItemDetailsDialog(card)
            }
        }

        // Example banner click setup
        val bannerOne: ImageView = view.findViewById(R.id.topBrandsLabel)
        bannerOne.setOnClickListener {
            showBannerDetailsDialog()
        }
        val brandBurgerKing: ImageView = view.findViewById(R.id.brand_burger_king)
        val brandKFC: ImageView = view.findViewById(R.id.brand_kfc)
        val brandSubway: ImageView = view.findViewById(R.id.brand_subway)
        val brandDominos: ImageView = view.findViewById(R.id.brand_dominos)

        // Set click listeners to show dialog for each brand
        brandBurgerKing.setOnClickListener {
            showBrandDetailsDialog("Burger King", "Enjoy delicious burgers!", R.drawable.brand_burger_king)
        }
        brandKFC.setOnClickListener {
            showBrandDetailsDialog("KFC", "Finger-lickin' good chicken!", R.drawable.brand_kfc)
        }
        brandSubway.setOnClickListener {
            showBrandDetailsDialog("Subway", "Eat fresh with healthy subs!", R.drawable.brand_subway)
        }
        brandDominos.setOnClickListener {
            showBrandDetailsDialog("Dominos", "Get your favorite pizza!", R.drawable.brand_dominos)
        }

        return view
    }
    // Function to show a brand details dialog
    private fun showBrandDetailsDialog(brandName: String, description: String, imageResId: Int) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_brand_details, null)

        // Initialize dialog views
        val brandNameTextView: TextView = dialogView.findViewById(R.id.dialog_brand_name)
        val brandDescriptionTextView: TextView = dialogView.findViewById(R.id.dialog_brand_description)
        val brandImageView: ImageView = dialogView.findViewById(R.id.dialog_brand_image)

        // Set dialog content
        brandNameTextView.text = brandName
        brandDescriptionTextView.text = description
        brandImageView.setImageResource(imageResId)

        // Create and show the dialog
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Brand Selected")
            .setMessage("Do wanna order this")
            .setPositiveButton("Yes") { _, _ ->
                // Start the DeliveryFragment when button is clicked
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, DeliveryFragment()) // replace with your container ID
                transaction.addToBackStack(null)  // Optional: Add this fragment to the back stack
                transaction.commit()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()


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
                itemImage.setImageResource(R.drawable.trending_image) 
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

        addToCartButton.setOnClickListener {
            // Create a CartItem object based on the selected item
            val cartItem = CartItem(
                name = itemName.text.toString(),
                price = itemPrice.text.toString().substring(1).toDouble(), // Remove the dollar sign and convert to double
            )

            // Add the CartItem to the CartRepository
            CartRepository.addItem(cartItem)

            Toast.makeText(requireContext(), "${itemName.text} added to cart", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }


    // Function to show banner details dialog with a countdown timer
    private fun showBannerDetailsDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_banner_details, null)

        // Initialize dialog views
        val bannerName: TextView = dialogView.findViewById(R.id.dialog_banner_name)
        val bannerDescription: TextView = dialogView.findViewById(R.id.dialog_banner_description)
        val bannerImage: ImageView = dialogView.findViewById(R.id.dialog_banner_image)
        val bannerTimer: TextView = dialogView.findViewById(R.id.dialog_banner_timer)

        // Set banner details
        bannerName.text = "Limited Time Offer"
        bannerDescription.text = "Get 50% off on select items!"
        bannerImage.setImageResource(R.drawable.banner2)

        // Countdown timer for 5 minutes (300,000 milliseconds)
        val countdownTime = 300000L
        val timer = object : CountDownTimer(countdownTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                bannerTimer.text = "Expires in: $minutes:${if (seconds < 10) "0" else ""}$seconds"
            }

            override fun onFinish() {
                bannerTimer.text = "Expired"
            }
        }
        timer.start()

        // Show the dialog
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(dialogView)
        val dialog = dialogBuilder.create()

        dialog.setOnDismissListener {
            timer.cancel()
        }

        dialog.show()

        // Use Handler to delay the navigation to the DeliveryFragment by 10 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            // Close the dialog before navigating
            if (dialog.isShowing) {
                dialog.dismiss()
            }

            // Navigate to DeliveryFragment after closing the dialog
            navigateToDeliveryFragment()
        }, 10000) // 10,000 milliseconds = 10 seconds
    }

    private fun navigateToDeliveryFragment() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, DeliveryFragment()) // replace with your container ID
        transaction.addToBackStack(null)  // Optional: Add this fragment to the back stack
        transaction.commit()
    }


    // Extension function to toggle visibility of spinners
    private fun View.toggleVisibility() {
        visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }
}
