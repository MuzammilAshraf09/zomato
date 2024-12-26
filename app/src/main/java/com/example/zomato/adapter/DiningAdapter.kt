package com.example.zomato.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zomato.R
import com.example.zomato.model.DiningItem

class DiningAdapter(
    private val items: List<DiningItem>,
    private val onAddToCartClick: (DiningItem) -> Unit,
    private val onUpdateClick: (DiningItem) -> Unit,  // Callback for update
    private val onDeleteClick: (DiningItem) -> Unit   // Callback for delete
) : RecyclerView.Adapter<DiningAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgDish: ImageView = view.findViewById(R.id.imgDish)
        val tvRestaurantName: TextView = view.findViewById(R.id.tvRestaurantName)
        val tvDishName: TextView = view.findViewById(R.id.tvDishName)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvDeliveryInfo: TextView = view.findViewById(R.id.tvDeliveryInfo)
        val tvDiscount: TextView = view.findViewById(R.id.tvDiscount)
        val btnAddToCart: Button = view.findViewById(R.id.btnAddToCart)
        val btnUpdate: Button = view.findViewById(R.id.btnUpdate)   // Update button
        val btnDelete: Button = view.findViewById(R.id.btnDelete)   // Delete button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dining_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.imgDish.setImageResource(item.imageResId) // Local drawable image
        holder.tvRestaurantName.text = item.restaurantName
        holder.tvDishName.text = item.dishName
        holder.tvPrice.text = "$${item.price}"
        holder.tvDeliveryInfo.text = item.deliveryInfo
        holder.tvDiscount.text = item.discountInfo

        // Set up button click listeners
        holder.btnAddToCart.setOnClickListener {
            onAddToCartClick(item)
        }

        holder.btnUpdate.setOnClickListener {
            onUpdateClick(item)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(item)
        }
    }

    override fun getItemCount(): Int = items.size
}
