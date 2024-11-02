package com.example.zomato

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class DeactivateFragment : Fragment() {

    private lateinit var deleteAccountButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deactivate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize delete button
        deleteAccountButton = view.findViewById(R.id.deleteAccountButton)

        // Set click listener for delete button
        deleteAccountButton.setOnClickListener {
            // Show a toast warning message for demonstration purposes
            Toast.makeText(
                requireContext(),
                "Your account has been deleted permanently!",
                Toast.LENGTH_LONG
            ).show()

            // Intent to navigate to StartActivity
            val intent = Intent(requireContext(), StartActivity::class.java)
            // Clear the back stack so the user can't navigate back to previous activities
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
