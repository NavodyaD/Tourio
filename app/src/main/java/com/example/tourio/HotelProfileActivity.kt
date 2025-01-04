package com.example.tourio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HotelProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotelprofile)

        //val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = intent.getStringExtra("userId") ?: return
        fetchHotelDetails(userId)
        val newTourButton: Button = findViewById(R.id.newTourButton)

        newTourButton.setOnClickListener {
            val intent = Intent(this, PreDefinedToursFormActivity::class.java)
            startActivity(intent)
        }

    }

    private fun fetchHotelDetails(userId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Hotels").whereEqualTo("userId", userId).get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    // get firebase field data to values
                    val document = result.documents[0]
                    val hotelName = document.getString("hotelName") ?: "N/A"
                    val hotelAddress = document.getString("hotelAddress") ?: "N/A"
                    val hotelDes = document.getString("hotelDescription") ?: "N/A"
                    val hotelFacilities = document.getString("hotelFacilities") ?: "N/A"
                    val hotelImageUrl = document.getString("hotelCoverImgURL") ?: "N/A"

                    // set values to text fields in details page
                    findViewById<TextView>(R.id.hotelProfileName).text = hotelName
                    findViewById<TextView>(R.id.hotelProfileAddress).text = hotelAddress
                    findViewById<TextView>(R.id.hotelProfileDescription).text = hotelDes
                    findViewById<TextView>(R.id.hotelProfileFacilities).text = hotelFacilities

                    val hotelCoverImage = findViewById<ImageView>(R.id.hotelProfileCoverImg)
                    if (hotelImageUrl.isNotEmpty()) {
                        Glide.with(this)
                            .load(hotelImageUrl)
                            .into(hotelCoverImage) // display the image in ImageView
                    } else {
                        hotelCoverImage.setImageResource(R.drawable.img_sigiriya_1)  // set default image if URL is null or empty
                    }
                } else {
                    Toast.makeText(this, "Cannot display hotel details", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error getting hotel details: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}