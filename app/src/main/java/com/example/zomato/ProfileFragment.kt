package com.example.zomato

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult

class ProfileFragment : Fragment() {

    private lateinit var profileImageView: ImageView
    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPhone: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        profileImageView = view.findViewById(R.id.profileImageView)
        editName = view.findViewById(R.id.editName)
        editEmail = view.findViewById(R.id.editEmail)
        editPhone = view.findViewById(R.id.editMobile)
        saveButton = view.findViewById(R.id.saveButton)

        // Pre-fill fields if arguments are available (optional)
        arguments?.let {
            editName.setText(it.getString("USER_NAME", ""))
            editEmail.setText(it.getString("USER_EMAIL", ""))
            editPhone.setText(it.getString("USER_PHONE", ""))
        }

        // Handle Save Button Click
        saveButton.setOnClickListener {
            val updatedName = editName.text.toString()
            val updatedEmail = editEmail.text.toString()
            val updatedPhone = editPhone.text.toString()

            // Pass data to AccountFragment using setFragmentResult
            val resultBundle = Bundle().apply {
                putString("USER_NAME", updatedName)
                putString("USER_EMAIL", updatedEmail)
                putString("USER_PHONE", updatedPhone)
            }
            setFragmentResult("userDetailsKey", resultBundle)

            // Navigate back to AccountFragment
            parentFragmentManager.popBackStack()
        }

        // Handle Profile Picture Click (Optional)
        profileImageView.setOnClickListener {
            // Logic for updating the profile picture (if needed)
        }
    }
}
