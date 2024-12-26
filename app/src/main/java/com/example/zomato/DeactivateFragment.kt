package com.example.zomato

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DeactivateFragment : Fragment() {

    private lateinit var deleteAccountButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_deactivate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        deleteAccountButton = view.findViewById(R.id.deleteAccountButton)
        progressBar = view.findViewById(R.id.progressBar)

        // Set click listener for delete button
        deleteAccountButton.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Deletion")
            .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                deleteAccount()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteAccount() {
        // Show progress bar and disable the button
        progressBar.visibility = View.VISIBLE
        deleteAccountButton.isEnabled = false

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            // Remove user from Firebase Authentication
            user.delete()
                .addOnCompleteListener { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Account successfully removed from authentication.",
                            Toast.LENGTH_LONG
                        ).show()

                        // Redirect to StartActivity
                        val intent = Intent(requireContext(), StartActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        deleteAccountButton.isEnabled = true
                        Toast.makeText(
                            requireContext(),
                            "Failed to remove account: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                .addOnFailureListener { e ->
                    progressBar.visibility = View.GONE
                    deleteAccountButton.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        "Error while removing account: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        } else {
            progressBar.visibility = View.GONE
            deleteAccountButton.isEnabled = true

            Toast.makeText(requireContext(), "No user is logged in.", Toast.LENGTH_SHORT).show()
        }
    }


}
