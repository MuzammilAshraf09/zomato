package com.example.zomato.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zomato.R
import com.example.zomato.model.CartItem

class CartAdapter(
    private val cartItems: MutableList<CartItem>,
    private val onRemoveClick: (CartItem) -> Unit // Lambda for removing item
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemPrice: TextView = itemView.findViewById(R.id.itemPrice)
        val removeButton: Button = itemView.findViewById(R.id.btnRemove)

        init {
            // Set click listener on the remove button
            removeButton.setOnClickListener {
                val item = cartItems[adapterPosition]
                onRemoveClick(item) // Call the lambda with the item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.itemName.text = cartItem.name
        holder.itemPrice.text = "₹${cartItem.price}"
    }

    override fun getItemCount(): Int = cartItems.size

    // Function to remove an item and notify the adapter
    fun removeItem(item: CartItem) {
        val position = cartItems.indexOf(item)
        if (position >= 0) {
            cartItems.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartItems.size)
        }
    }
}
