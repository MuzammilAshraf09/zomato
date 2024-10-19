package com.example.zomato

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var userLocationTextView: TextView
    private lateinit var menuDrawer: ImageView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var overlayView: View
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize views using findViewById with correct IDs
        userLocationTextView = findViewById(R.id.userLocationTextView)
        menuDrawer = findViewById(R.id.menu_drawer)
        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        overlayView = findViewById(R.id.overlay)
        navigationView = findViewById(R.id.nav_view)  // Initialize NavigationView

        // Retrieve and set the user location
        val userLocation = intent.getStringExtra("USER_LOCATION")
        userLocationTextView.text = userLocation ?: "Location not set"

        // Bottom Navigation listener
        bottomNavigationView.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.nav_account) {
                startActivity(Intent(this, AccountActivity::class.java))
                true
            } else {
                false
            }
        }

        // Handle window insets (for adjusting system bar padding)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBarsInsets.left, systemBarsInsets.top, systemBarsInsets.right, systemBarsInsets.bottom)
            insets
        }

        // Drawer menu click
        menuDrawer.setOnClickListener {
            showDrawerMenu()
        }

        // Close drawer when needed
        overlayView.setOnClickListener {
            closeDrawerMenu()
        }

        // Set up navigation item selection listener
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_settings -> {
                    // Start SettingsActivity
                    startActivity(Intent(this, SettingActivity::class.java))
                    closeDrawerMenu()  // Close the drawer after selection
                    true
                }
                R.id.nav_orders -> {
                    // Handle your orders option here (if needed)
                    closeDrawerMenu()  // Close the drawer after selection
                    true
                }
                else -> false
            }
        }
    }

    private fun showDrawerMenu() {
        drawerLayout.openDrawer(GravityCompat.END)
        overlayView.visibility = View.VISIBLE
    }

    private fun closeDrawerMenu() {
        drawerLayout.closeDrawer(GravityCompat.END)
        overlayView.visibility = View.GONE
    }
}
