package com.example.zomato

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AccountActivity : AppCompatActivity() {

    private lateinit var userNameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var backArrow: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        // Initialize views
        userNameTextView = findViewById(R.id.userName)
        emailTextView = findViewById(R.id.userEmail)
        phoneTextView = findViewById(R.id.userPhone)
        backArrow = findViewById(R.id.backArrow)

        // Get the data passed from ProfileActivity
        val userName = intent.getStringExtra("USER_NAME")
        val userEmail = intent.getStringExtra("USER_EMAIL")
        val userPhone = intent.getStringExtra("USER_PHONE")

        // Display the data in the respective TextViews
        if (userName != null) {
            userNameTextView.text = userName
        }
        if (userEmail != null) {
            emailTextView.text = "Email: $userEmail"
        }
        if (userPhone != null) {
            phoneTextView.text = "Phone: $userPhone"
        }

        // Set OnClickListener for the back arrow to navigate to the Home screen
        backArrow.setOnClickListener {
            val intent = Intent(this@AccountActivity, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }
}
