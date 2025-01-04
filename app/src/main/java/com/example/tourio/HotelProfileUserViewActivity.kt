package com.example.tourio

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class HotelProfileUserViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotelprofileuserview)

        val userId = intent.getStringExtra("userId") ?: return
        fetchHotelDetails(userId)
    }

    private fun fetchHotelDetails(userId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Hotels").whereEqualTo("userId", userId).get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    // get firebase field data to values
                    val document = result.documents[0]
                    val hotelName = document.getString("hotelName")
                    val hotelAddress = document.getString("hotelAddress")
                    val hotelDes = document.getString("hotelDescription")
                    val hotelFacilities = document.getString("hotelFacilities")
                    val hotelImageUrl = document.getString("hotelCoverImgURL")

                    // set values to text fields in details page
                    findViewById<TextView>(R.id.hotelName).text = hotelName
                    findViewById<TextView>(R.id.hotelAddress).text = hotelAddress
                    findViewById<TextView>(R.id.hotelDes).text = hotelDes
                    findViewById<TextView>(R.id.hotelFacilities).text = hotelFacilities

                    val hotelCoverImage = findViewById<ImageView>(R.id.hotelUserViewProfileCoverImg)
                    if (!hotelImageUrl.isNullOrEmpty()) {
                        Glide.with(this)
                            .load(hotelImageUrl)  // load the image URL from Firebase
                            .into(hotelCoverImage) // display the image in ImageView
                    } else {
                        hotelCoverImage.setImageResource(R.drawable.img_defaultimage)  // set default image if URL is null or empty
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