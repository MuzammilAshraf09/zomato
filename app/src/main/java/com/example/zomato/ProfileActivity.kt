package com.example.zomato

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImageView: ImageView
    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPhone: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize views
        profileImageView = findViewById(R.id.profileImageView)
        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)
        editPhone = findViewById(R.id.editMobile)
        saveButton = findViewById(R.id.saveButton)

        // Handle Save Button Click
        saveButton.setOnClickListener {
            val updatedName = editName.text.toString()
            val updatedEmail = editEmail.text.toString()
            val updatedPhone = editPhone.text.toString()

            // Create an Intent to navigate to AccountActivity
            val intent = Intent(this@ProfileActivity, AccountActivity::class.java)

            // Pass the entered data to AccountActivity
            intent.putExtra("USER_NAME", updatedName)
            intent.putExtra("USER_EMAIL", updatedEmail)
            intent.putExtra("USER_PHONE", updatedPhone)

            // Start AccountActivity
            startActivity(intent)
        }

        // Handle Profile Picture Click (for updating)
        profileImageView.setOnClickListener {
            // Logic to change profile picture (e.g., open gallery or camera)
        }
    }
}
