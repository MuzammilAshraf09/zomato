package com.example.zomato

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.TextView
import android.content.Intent



import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        // Reference the TextView
        val transitionText: TextView = findViewById(R.id.transitionText)

        // Create a fade-in animation
        val fadeIn = AlphaAnimation(0.3f, 1f).apply {
            duration = 1000
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
        }

        // Create a scale animation for a slight zoom effect
        val scale = ScaleAnimation(
            1.0f, 1.1f,  // Start and end values for the X axis scaling
            1.0f, 1.1f,  // Start and end values for the Y axis scaling
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
            Animation.RELATIVE_TO_SELF, 0.5f  // Pivot point of Y scaling
        ).apply {
            duration = 1000
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
        }

        // Combine animations
        val animationSet = AnimationSet(true).apply {
            addAnimation(fadeIn)
            addAnimation(scale)
        }

        // Start the animation
        transitionText.visibility = View.VISIBLE
        transitionText.startAnimation(animationSet)

        // Reference the Continue Button
        val continueButton: Button = findViewById(R.id.continueButton)

        // Set an OnClickListener to launch HomeActivity
        continueButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Optional: Call finish() if you don't want to return to StartActivity
        }
    }
}