package com.example.zomato

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class LocationActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        // Initialize Firebase Authentication and Database
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()

        // Get the current authenticated user
        val currentUser: FirebaseUser? = firebaseAuth.currentUser

        // Get user details
        val userName = currentUser?.displayName ?: "Guest"
        val userEmail = currentUser?.email ?: "N/A"
        val userPhone = currentUser?.phoneNumber ?: "N/A"
        val userPhotoUrl = currentUser?.photoUrl?.toString() ?: ""

        // Reference the AutoCompleteTextView for location input
        val locationInput: AutoCompleteTextView = findViewById(R.id.locationInput)

        // Reference the dropdown icon ImageView
        val dropdownIcon: ImageView = findViewById(R.id.dropdownIcon)

        // List of locations for the dropdown
        val locations = listOf(
            "Satellite Town Gujranwala",
            "Model Town Gujranwala",
            "Civil Lines Gujranwala",
            "Peoples Colony Gujranwala",
            "Wapda Town Gujranwala",
            "Garden Town Gujranwala"
        )

        // Set up the ArrayAdapter with location suggestions
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            locations
        )

        // Connect the adapter to the AutoCompleteTextView
        locationInput.setAdapter(adapter)

        // Show dropdown list when AutoCompleteTextView is clicked
        locationInput.setOnClickListener {
            locationInput.showDropDown()
        }

        // Show dropdown list when dropdown icon is clicked
        dropdownIcon.setOnClickListener {
            locationInput.showDropDown()
        }

        // Reference the "Set Location" button
        val setLocationButton: Button = findViewById(R.id.setLocationButton)

        // Reference the locations node in Firebase Database
        val locationsRef = firebaseDatabase.getReference("locations")

        // Check if locations already exist in the database
        locationsRef.get().addOnSuccessListener { snapshot ->
            if (!snapshot.exists()) {
                // If no locations exist, store the list in Firebase Database
                locationsRef.setValue(locations)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Locations added to Firebase", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Failed to add locations: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        // Set an OnClickListener on the "Set Location" button
        setLocationButton.setOnClickListener {
            // Get the entered location from the AutoCompleteTextView
            val location = locationInput.text.toString()

            // Check if the location is one of the predefined locations
            if (locations.contains(location)) {
                // Show a toast message indicating the location is set
                Toast.makeText(this, "Location Set: $location", Toast.LENGTH_SHORT).show()

                // Pass the location and user data to HomeActivity
                val intent = Intent(this, HomeActivity::class.java).apply {
                    putExtra("LOCATION", location)
                    putExtra("USER_NAME", userName)
                    putExtra("USER_EMAIL", userEmail)
                    putExtra("USER_PHONE", userPhone)
                    putExtra("USER_PHOTO_URL", userPhotoUrl)
                }
                startActivity(intent)
                finish() // Optional: Call finish() if you don't want to return to LocationActivity
            } else {
                // Show a message if no valid location is entered
                Toast.makeText(this, "Please select a valid location from the list", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
