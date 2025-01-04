package com.example.tourio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class TravelerProfileActivity : AppCompatActivity()
{
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travelerprofile)

        val logOutButton = findViewById<Button>(R.id.travelerLogoutButton)

        logOutButton.setOnClickListener {
            // Log the user out of Firebase Authentication (if using Firebase)
            FirebaseAuth.getInstance().signOut()

            // Optional: Clear any other session data or preferences
            val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()

            // Log the logout action
            Log.d("Logout", "User has logged out")

            // Redirect to the Get Started or Login page
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clear back stack
            startActivity(intent)

            // Finish the current activity (optional)
            finish()
        }
    }
}