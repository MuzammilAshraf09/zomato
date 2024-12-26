package com.example.zomato.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zomato.R
import com.example.zomato.model.DeliveryItem

class DeliveryAdapter(
    private val items: List<DeliveryItem>,                   // List of delivery/cart items
    private val onAddToCartClick: (DeliveryItem) -> Unit,    // Callback for "Add to Cart"
    private val onUpdateClick: (DeliveryItem) -> Unit,       // Callback for "Update"
    private val onDeleteClick: (DeliveryItem) -> Unit        // Callback for "Delete"
) : RecyclerView.Adapter<DeliveryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgDish: ImageView = view.findViewById(R.id.imgDish)              // Image of the dish
        val tvRestaurantName: TextView = view.findViewById(R.id.tvRestaurantName) // Restaurant name
        val tvDishName: TextView = view.findViewById(R.id.tvDishName)        // Dish name
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)              // Price of the dish
        val tvDeliveryInfo: TextView = view.findViewById(R.id.tvDeliveryInfo) // Delivery info (e.g., ETA)
        val tvDiscount: TextView = view.findViewById(R.id.tvDiscount)        // Discount information
        val btnAddToCart: Button = view.findViewById(R.id.btnAddToCart)      // "Add to Cart" button
        val btnUpdate: Button = view.findViewById(R.id.btnUpdate)            // "Update" button
        val btnDelete: Button = view.findViewById(R.id.btnDelete)            // "Delete" button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_delivery, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        // Set item data to the views
        holder.imgDish.setImageResource(item.imageResId) // Local drawable image for the dish
        holder.tvRestaurantName.text = item.restaurantName
        holder.tvDishName.text = item.dishName
        holder.tvPrice.text = "$${item.price}"          // Format the price with a dollar sign
        holder.tvDeliveryInfo.text = item.deliveryInfo
        holder.tvDiscount.text = item.discountInfo

        // Set click listeners for buttons
        holder.btnAddToCart.setOnClickListener {
            onAddToCartClick(item)  // Add to Cart action
        }
        holder.btnUpdate.setOnClickListener {
            onUpdateClick(item)        // Update action
        }
        holder.btnDelete.setOnClickListener {
            onDeleteClick(item)        // Delete action
        }
    }

    override fun getItemCount(): Int = items.size // Return the size of the items list
}
