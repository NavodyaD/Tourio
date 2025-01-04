package com.example.tourio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflater.inflate(R.layout.activity_travelerprofile, container, false)

        // Find the logout button
        val logOutButton = binding.findViewById<Button>(R.id.travelerLogoutButton)

        logOutButton.setOnClickListener {
            // Log the user out of Firebase Authentication (if using Firebase)
            FirebaseAuth.getInstance().signOut()

            // Optional: Clear any other session data or preferences
            val sharedPreferences = requireActivity().getSharedPreferences("AppPrefs", android.content.Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()

            // Log the logout action
            Log.d("Logout", "User has logged out")

            // Redirect to the Get Started or Login page (MainActivity in this case)
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clear back stack
            startActivity(intent)

            // Finish the current activity (optional)
            requireActivity().finish()
        }

        return binding
    }
}
