package com.example.tourio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class HotelDashBoardActivity : AppCompatActivity() {

    private lateinit var profileBox: LinearLayout
    private lateinit var yourToursBox: LinearLayout
    private lateinit var travelRequestBox: LinearLayout
    private lateinit var viewHomeBox: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hoteldashboard)

        val userId = intent.getStringExtra("userId") ?: return

        // Initialize the boxes
        profileBox = findViewById(R.id.profileBox)
        yourToursBox = findViewById(R.id.yourToursBox)
        travelRequestBox = findViewById(R.id.travelRequestBox)
        viewHomeBox = findViewById(R.id.viewHomeBox)

        // Set click listeners for navigation
        profileBox.setOnClickListener {
            val intent = Intent(this, HotelProfileActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        yourToursBox.setOnClickListener {
            val intent = Intent(this, PublishedRequestsPageActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }

        travelRequestBox.setOnClickListener {
            //val intent = Intent(this, TravelerProfileActivity::class.java)
            //startActivity(intent)
        }

        viewHomeBox.setOnClickListener {
            val intent = Intent(this, PreDefinedToursFormActivity::class.java)
            startActivity(intent)
        }


        val logOutButton = findViewById<Button>(R.id.hotelDBLogoutButton)

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
