package com.example.zomato

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AccountActivity : AppCompatActivity() {

    private lateinit var userNameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        // Initialize the TextView
        userNameTextView = findViewById(R.id.userName)

        // Get the name passed from ProfileActivity
        val userName = intent.getStringExtra("USER_NAME")

        // Display the name in the TextView
        if (userName != null) {
            userNameTextView.text = userName
        }
    }
}
