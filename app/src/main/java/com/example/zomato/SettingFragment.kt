package com.example.zomato

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class SettingFragment : Fragment() {

    private lateinit var title: TextView
    private lateinit var sectionEditProfile: TextView
    private lateinit var sectionDeactivate: TextView
    private lateinit var sectionHelpSupport: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        title = view.findViewById(R.id.title)
        sectionEditProfile = view.findViewById(R.id.sectionEditProfile)
        sectionDeactivate = view.findViewById(R.id.sectionDeactivateAccount) // Updated ID
        sectionHelpSupport = view.findViewById(R.id.sectionSupport)

        // Set title text
        title.text = "Settings"

        // Set OnClickListener for "Edit Profile"
        sectionEditProfile.setOnClickListener {
            // Navigate to ProfileFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment())
                .addToBackStack(null)
                .commit()
        }

        // Set OnClickListener for "Deactivate Account"
        sectionDeactivate.setOnClickListener {
            // Navigate to DeactivateFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DeactivateFragment())
                .addToBackStack(null)
                .commit()
        }

        // Set OnClickListener for "Help and Support"
        sectionHelpSupport.setOnClickListener {
            // Handle click for help and support
        }
    }
}
