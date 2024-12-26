package com.example.zomato

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SettingFragment : Fragment() {

    private lateinit var title: TextView
    private lateinit var sectionEditProfile: TextView
    private lateinit var sectionDeactivate: TextView
    private lateinit var sectionHelpSupport: TextView

    private lateinit var progressView: View // Progress layout for deactivation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        title = view.findViewById(R.id.title)
        sectionEditProfile = view.findViewById(R.id.sectionEditProfile)
        sectionDeactivate = view.findViewById(R.id.sectionDeactivateAccount)
        sectionHelpSupport = view.findViewById(R.id.sectionSupport)
        progressView = layoutInflater.inflate(R.layout.fragment_deactivate, null)

        // Set title text
        title.text = "Settings"

        // Set OnClickListener for "Edit Profile"
        sectionEditProfile.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment())
                .addToBackStack(null)
                .commit()
        }

        // Set OnClickListener for "Deactivate Account"
        sectionDeactivate.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DeactivateFragment())
                .addToBackStack(null)
                .commit()
        }


        // Set OnClickListener for "Help and Support"
        sectionHelpSupport.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HelpSupportFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    
    private fun hideDeactivationProgress() {
        val parentView = requireActivity().findViewById<ViewGroup>(android.R.id.content)
        parentView.removeView(progressView)
    }

    private fun deactivateAccount() {
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid

        if (user != null && userId != null) {
            // Delete user data from Firestore
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("users").document(userId)
                .delete()
                .addOnSuccessListener {
                    // Once data is deleted, delete the user from Firebase Authentication
                    user.delete()
                        .addOnCompleteListener { task ->
                            hideDeactivationProgress()
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    requireContext(),
                                    "Account successfully deactivated.",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Move to StartActivity
                                val intent = Intent(requireContext(), StartActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Failed to deactivate account: ${task.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
                .addOnFailureListener { e ->
                    hideDeactivationProgress()
                    Toast.makeText(
                        requireContext(),
                        "Failed to remove account data: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            hideDeactivationProgress()
            Toast.makeText(requireContext(), "No user is logged in.", Toast.LENGTH_SHORT).show()
        }
    }
}
