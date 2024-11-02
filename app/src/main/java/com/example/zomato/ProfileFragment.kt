package com.example.zomato

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment

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
        // Inflate the layout for this fragment
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

        // Handle Save Button Click
        saveButton.setOnClickListener {
            val updatedName = editName.text.toString()
            val updatedEmail = editEmail.text.toString()
            val updatedPhone = editPhone.text.toString()

            // Create an instance of AccountFragment
            val accountFragment = AccountFragment()

            // Pass the entered data to AccountFragment using Bundle
            val bundle = Bundle()
            bundle.putString("USER_NAME", updatedName)
            bundle.putString("USER_EMAIL", updatedEmail)
            bundle.putString("USER_PHONE", updatedPhone)
            accountFragment.arguments = bundle

            // Navigate to AccountFragment
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container, accountFragment)
            fragmentTransaction?.addToBackStack(null) // Optional, to add it to the back stack
            fragmentTransaction?.commit()
        }

        // Handle Profile Picture Click (for updating)
        profileImageView.setOnClickListener {
            // Logic to change profile picture (e.g., open gallery or camera)
        }
    }
}
