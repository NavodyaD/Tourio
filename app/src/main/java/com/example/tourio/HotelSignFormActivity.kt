package com.example.tourio

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HotelSignFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotelsignform)

        val continueButton = findViewById<Button>(R.id.buttonContinue)

        continueButton.setOnClickListener {
            addHotelDetails()
        }
    }

    private fun addHotelDetails() {
        val db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            // Get data from EditText fields
            val hotelName = findViewById<EditText>(R.id.hotelName).text.toString()
            val hotelCustomID = findViewById<EditText>(R.id.hotelCustomID).text.toString()
            val hotelAddress = findViewById<EditText>(R.id.hotelAddress).text.toString()
            val hotelCoverImgURL = findViewById<EditText>(R.id.hotelCoverImgURL).text.toString()
            val hotelDescription = findViewById<EditText>(R.id.hotelDescription).text.toString()
            val hotelFacilities = findViewById<EditText>(R.id.hotelFacilities).text.toString()

            if (hotelName.isEmpty() || hotelCustomID.isEmpty() || hotelAddress.isEmpty() || hotelCoverImgURL.isEmpty() || hotelDescription.isEmpty() || hotelFacilities.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
                return
            }

            // Create a map to store the hotel data
            val hotelData = hashMapOf(
                "hotelName" to hotelName,
                "hotelCustomID" to hotelCustomID,
                "hotelAddress" to hotelAddress,
                "hotelCoverImgURl" to hotelCoverImgURL,
                "hotelDescription" to hotelDescription,
                "hotelFacilities" to hotelFacilities,
                "userId" to userId // Associate with current user's ID
            )

            // Add data to the 'Hotels' collection
            db.collection("Hotels")
                .add(hotelData) // Automatically generates a document ID
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Hotel account setup successful!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to setup hotel profile: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            // If user is not logged in
            Toast.makeText(this, "Cannot Identify the User", Toast.LENGTH_SHORT).show()
        }
    }
}