package com.example.zomato

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class AccountFragment : Fragment() {
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

        // Get the data passed from ProfileFragment via Bundle
        val bundle = arguments
        val userName = bundle?.getString("USER_NAME", "Guest")
        val userEmail = bundle?.getString("USER_EMAIL", "N/A")
        val userPhone = bundle?.getString("USER_PHONE", "N/A")

        // Display the data in the respective TextViews
        userNameTextView.text = userName
        emailTextView.text = "Email: $userEmail"
        phoneTextView.text = "Phone: $userPhone"
    }
}
