package com.example.zomato

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.zomato.CartFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize views
        drawerLayout = findViewById(R.id.menu_drawer)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        navigationView = findViewById(R.id.nav_view)

        // Set up initial fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        // Bottom Navigation listener
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_account -> {
                    replaceFragment(AccountFragment())
                    true
                }

                R.id.nav_delivery -> {
                    replaceFragment(DeliveryFragment())
                    true
                }
                R.id.nav_cart -> {  // Add this block for cart navigation
                    replaceFragment(CartFragment())
                    true
                }
                R.id.nav_dining -> {
                    replaceFragment(DiningFragment())
                    true
                }
                else -> false
            }
        }

        // Drawer navigation listener
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_setting -> {
                    replaceFragment(SettingFragment())
                    drawerLayout.closeDrawer(GravityCompat.END)
                    true
                }
                R.id.nav_payment -> {
                    replaceFragment(PaymentFragment())
                    drawerLayout.closeDrawer(GravityCompat.END)

                    true
                }
                else -> false
            }
        }
    }

    // Function to replace fragments
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    // Function to open drawer from fragment
    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.END)
    }

    // Function to close drawer from fragment
    fun closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.END)
    }
}
