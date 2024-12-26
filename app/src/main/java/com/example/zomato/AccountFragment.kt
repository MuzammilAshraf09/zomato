package com.example.zomato

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class AccountFragment : Fragment() {

    // Declare views
    private lateinit var userNameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        userNameTextView = view.findViewById(R.id.userName)
        emailTextView = view.findViewById(R.id.userEmail)
        phoneTextView = view.findViewById(R.id.userPhone)

        // Load persisted data
        val sharedPreferences = requireContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
        val persistedName = sharedPreferences.getString("USER_NAME", "Guest")
        val persistedEmail = sharedPreferences.getString("USER_EMAIL", "Email not provided")
        val persistedPhone = sharedPreferences.getString("USER_PHONE", "Phone not available")

        // Retrieve initial data from arguments or SharedPreferences
        val bundle = arguments
        val userName = bundle?.getString("USER_NAME") ?: persistedName
        val userEmail = bundle?.getString("USER_EMAIL") ?: persistedEmail
        val userPhone = bundle?.getString("USER_PHONE") ?: persistedPhone

        // Display initial data
        displayUserDetails(userName, userEmail, userPhone)

        // Hide views with no data
        if (userName == "Guest") userNameTextView.visibility = View.GONE
        if (userEmail == "Email not provided") emailTextView.visibility = View.GONE
        if (userPhone == "Phone not available") phoneTextView.visibility = View.GONE

        // Listen for updates from ProfileFragment
        parentFragmentManager.setFragmentResultListener("userDetailsKey", this) { _, result ->
            val updatedName = result.getString("USER_NAME", "Guest")
            val updatedEmail = result.getString("USER_EMAIL", "Email not provided")
            val updatedPhone = result.getString("USER_PHONE", "Phone not available")

            // Save updated data to SharedPreferences
            saveUserDetails(updatedName, updatedEmail, updatedPhone)

            // Update TextViews with new data
            displayUserDetails(updatedName, updatedEmail, updatedPhone)

            // Ensure views are visible after updates
            userNameTextView.visibility = View.VISIBLE
            emailTextView.visibility = View.VISIBLE
            phoneTextView.visibility = View.VISIBLE
        }
    }

    // Helper function to display user details
    private fun displayUserDetails(userName: String?, userEmail: String?, userPhone: String?) {
        userNameTextView.text = userName ?: "Guest"
        emailTextView.text = "Email: ${userEmail ?: "Email not provided"}"
        phoneTextView.text = "Phone: ${userPhone ?: "Phone not available"}"
    }

    // Save data to SharedPreferences
    private fun saveUserDetails(userName: String?, userEmail: String?, userPhone: String?) {
        val sharedPreferences = requireContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("USER_NAME", userName)
        editor.putString("USER_EMAIL", userEmail)
        editor.putString("USER_PHONE", userPhone)
        editor.apply()
    }

    // Factory method to create an instance of AccountFragment with data
    companion object {
        fun newInstance(userName: String, userEmail: String, userPhone: String): AccountFragment {
            val fragment = AccountFragment()
            val args = Bundle().apply {
                putString("USER_NAME", userName)
                putString("USER_EMAIL", userEmail)
                putString("USER_PHONE", userPhone)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
