package com.example.hotelapp

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.tourio.HomePageActivity
import com.example.tourio.HotelProfileActivity
import com.example.tourio.PreDefinedToursFormActivity
import com.example.tourio.R
import com.example.tourio.TravelerProfileActivity

class HotelDashBoardActivity : AppCompatActivity() {

    private lateinit var profileBox: LinearLayout
    private lateinit var yourToursBox: LinearLayout
    private lateinit var travelRequestBox: LinearLayout
    private lateinit var viewHomeBox: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hoteldashboard)

        // Initialize the boxes
        profileBox = findViewById(R.id.profileBox)
        yourToursBox = findViewById(R.id.yourToursBox)
        travelRequestBox = findViewById(R.id.travelRequestBox)
        viewHomeBox = findViewById(R.id.viewHomeBox)

        // Set click listeners for navigation
        profileBox.setOnClickListener {
            val intent = Intent(this, HotelProfileActivity::class.java)
            startActivity(intent)
        }

        yourToursBox.setOnClickListener {
            val intent = Intent(this, PreDefinedToursFormActivity::class.java)
            startActivity(intent)
        }

        travelRequestBox.setOnClickListener {
            val intent = Intent(this, TravelerProfileActivity::class.java)
            startActivity(intent)
        }

        viewHomeBox.setOnClickListener {
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }
    }
}
