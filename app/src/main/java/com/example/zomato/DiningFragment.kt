package com.example.zomato

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zomato.adapter.DeliveryAdapter
import com.example.zomato.model.DeliveryItem
import com.example.zomato.adapter.CartAdapter
import com.example.zomato.repository.CartRepository
import com.example.zomato.model.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DiningFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DeliveryAdapter
    private val deliveryItems = mutableListOf<DeliveryItem>()
    private lateinit var btnAddItem: Button
    private lateinit var etSearch: EditText
    private val filteredItems = mutableListOf<DeliveryItem>()
    private val cartItems = mutableListOf<DeliveryItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dining, container, false)

        // Initialize views
        recyclerView = view.findViewById(R.id.rvDeliveryItems)
        etSearch = view.findViewById(R.id.searchBar)
        btnAddItem = view.findViewById(R.id.btnAddItem)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Show "Add Item" button for specific email
        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email

        btnAddItem.visibility = if (currentUserEmail == "muzammilkemu786@gmail.com") View.VISIBLE else View.GONE

        btnAddItem.setOnClickListener { showAddItemForm() }

// Initialize Adapter for cart functionality
        adapter = DeliveryAdapter(filteredItems, { item ->
            // Add the item to the cart
            Toast.makeText(requireContext(), "${item.dishName} added to cart!", Toast.LENGTH_SHORT).show()
            addDeliveryItemToCart(item)  // This function should handle adding items to the cart (local or Firebase)
        }, { item ->
            // Handle update action (only for admin)
            if (currentUserEmail == "muzammilkemu786@gmail.com") {
                showUpdateItemForm(item)  // Pass item for updating
            } else {
                Toast.makeText(requireContext(), "Sorry! Only Admin has access to this!", Toast.LENGTH_SHORT).show()
            }
        }, { item ->
            // Handle delete action (only for admin)
            if (currentUserEmail == "muzammilkemu786@gmail.com") {
                deleteItem(item)  // Handle item deletion
            } else {
                Toast.makeText(requireContext(), "Sorry! Only Admin has access to this!", Toast.LENGTH_SHORT).show()
            }
        })


        recyclerView.adapter = adapter

        // Fetch items from Firebase
        fetchDeliveryItems()
        fetchCartItems()

        // Set up search functionality
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterItems(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }

    private fun fetchDeliveryItems() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("deliveryItems")

        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                deliveryItems.clear()

                if (!snapshot.exists()) {
                    hardcodedItems.forEach { item ->
                        databaseRef.push().setValue(item)
                    }
                    deliveryItems.addAll(hardcodedItems)
                } else {
                    for (itemSnapshot in snapshot.children) {
                        val item = itemSnapshot.getValue(DeliveryItem::class.java)
                        if (item != null) {
                            deliveryItems.add(item)
                        }
                    }
                }
                filteredItems.clear()
                filteredItems.addAll(deliveryItems)
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to fetch items: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun deleteItem(item: DeliveryItem) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("deliveryItems")
        val itemRef = databaseRef.orderByChild("name").equalTo(item.name).limitToFirst(1)

        itemRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    snapshot.children.first().ref.removeValue()
                    deliveryItems.remove(item)
                    filteredItems.remove(item)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(requireContext(), "${item.name} has been deleted.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to delete item: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showUpdateItemForm(item: DeliveryItem) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_item, null)
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)
            .create()

        val etDishName: EditText = dialogView.findViewById(R.id.etDishName)
        val etRestaurantName: EditText = dialogView.findViewById(R.id.etRestaurantName)
        val etPrice: EditText = dialogView.findViewById(R.id.etPrice)
        val etDeliveryInfo: EditText = dialogView.findViewById(R.id.etDeliveryInfo)
        val etDiscountInfo: EditText = dialogView.findViewById(R.id.etDiscountInfo)
        val btnSubmit: Button = dialogView.findViewById(R.id.btnSubmit)

        // Pre-fill the fields with the current item data
        etDishName.setText(item.name)
        etRestaurantName.setText(item.restaurantName)
        etPrice.setText(item.price.toString())
        etDeliveryInfo.setText(item.deliveryInfo)
        etDiscountInfo.setText(item.discountInfo)

        btnSubmit.setOnClickListener {
            val name = etDishName.text.toString().trim()
            val restaurant = etRestaurantName.text.toString().trim()
            val price = etPrice.text.toString().toDoubleOrNull() ?: 0.0
            val deliveryInfo = etDeliveryInfo.text.toString().trim()
            val discountInfo = etDiscountInfo.text.toString().trim()

            if (name.isNotEmpty() && restaurant.isNotEmpty() && price > 0) {
                val updatedItem = DeliveryItem(
                    name = name,
                    restaurantName = restaurant,
                    price = price,
                    deliveryInfo = deliveryInfo,
                    discountInfo = discountInfo,
                    imageResId = R.drawable.pizza // You can set the same image or change it
                )
                updateDeliveryItemInFirebase(item, updatedItem)
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields properly!", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun updateDeliveryItemInFirebase(originalItem: DeliveryItem, updatedItem: DeliveryItem) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("deliveryItems")
        val itemRef = databaseRef.orderByChild("name").equalTo(originalItem.name).limitToFirst(1)

        itemRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (itemSnapshot in snapshot.children) {
                    itemSnapshot.ref.setValue(updatedItem) // Update item in Firebase

                    // Update the local list (deliveryItems and filteredItems) with the updated item
                    val index = deliveryItems.indexOfFirst { it.name == originalItem.name }
                    if (index != -1) {
                        deliveryItems[index] = updatedItem
                        filteredItems[index] = updatedItem // Update the filtered list as well
                    }

                    // Notify adapter to refresh the UI
                    adapter.notifyDataSetChanged()

                    Toast.makeText(requireContext(), "Item updated successfully", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to update item: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }



    private fun filterItems(query: String) {
        val lowerCaseQuery = query.lowercase()
        filteredItems.clear()

        if (query.isEmpty()) {
            filteredItems.addAll(deliveryItems)
        } else {
            deliveryItems.forEach { item ->
                // Check if the dish name or restaurant name starts with the search query
                if (item.name.lowercase().contains(lowerCaseQuery) ||
                    item.restaurantName.lowercase().contains(lowerCaseQuery)) {
                    filteredItems.add(item)
                }
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun addDeliveryItemToFirebase(item: DeliveryItem) {
        val databaseRef = FirebaseDatabase.getInstance().getReference("deliveryItems")
        databaseRef.push().setValue(item)
    }

    private fun showAddItemForm() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_item, null)
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)
            .create()

        val etDishName: EditText = dialogView.findViewById(R.id.etDishName)
        val etRestaurantName: EditText = dialogView.findViewById(R.id.etRestaurantName)
        val etPrice: EditText = dialogView.findViewById(R.id.etPrice)
        val etDeliveryInfo: EditText = dialogView.findViewById(R.id.etDeliveryInfo)
        val etDiscountInfo: EditText = dialogView.findViewById(R.id.etDiscountInfo)
        val btnSubmit: Button = dialogView.findViewById(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val name = etDishName.text.toString().trim()
            val restaurant = etRestaurantName.text.toString().trim()
            val price = etPrice.text.toString().toDoubleOrNull() ?: 0.0
            val deliveryInfo = etDeliveryInfo.text.toString().trim()
            val discountInfo = etDiscountInfo.text.toString().trim()

            if (name.isNotEmpty() && restaurant.isNotEmpty() && price > 0) {
                val newItem = DeliveryItem(
                    name = name,
                    restaurantName = restaurant,
                    price = price,
                    deliveryInfo = deliveryInfo,
                    discountInfo = discountInfo,
                    imageResId = R.drawable.pizza
                )
                addDeliveryItemToFirebase(newItem)
                deliveryItems.add(newItem)
                filterItems(etSearch.text.toString()) // Apply the current filter
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields properly!", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }


    private fun fetchCartItems() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("cartItems")
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartItems.clear()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(DeliveryItem::class.java)
                    if (item != null) {
                        cartItems.add(item)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to fetch cart items: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun addDeliveryItemToCart(item: DeliveryItem) {
        val cartItem = CartItem(item.name, item.price) // Convert to CartItem
        CartRepository.addItem(cartItem) // Add to the cart repository
        Toast.makeText(requireContext(), "${item.name} added to cart!", Toast.LENGTH_SHORT).show()
    }

    private val hardcodedItems = listOf(
        DeliveryItem(
            name = "Burger",
            restaurantName = "Burger King",
            price = 5.99,
            deliveryInfo = "Free delivery on orders above $20",
            discountInfo = "10% off today!",
            imageResId = R.drawable.burger
        ),
        DeliveryItem(
            name = "Pizza",
            restaurantName = "Domino's",
            price = 7.99,
            deliveryInfo = "Delivery in 30 mins",
            discountInfo = "15% off on orders above $15",
            imageResId = R.drawable.pizza
        )
    )
}
