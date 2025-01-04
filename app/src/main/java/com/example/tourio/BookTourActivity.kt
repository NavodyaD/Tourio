package com.example.tourio

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class BookTourActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booktour)

        val preDefTourId = intent.getStringExtra("preDefTourId") ?: return
        fetchBookDetails(preDefTourId)
    }
    private fun fetchBookDetails(preDefTourId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("PredefinedTours").whereEqualTo("preDefTourId", preDefTourId).get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    // get firebase field data to values
                    val document = result.documents[0]
                    val tourTitle = document.getString("tourTitle")
                    val tourFacilities = document.getString("facilities")
                    val tourPrice = document.getString("tourPrice")
                    val destination1 = document.getString("destination1")
                    val destination2 = document.getString("destination2")
                    val destination3 = document.getString("destination3")
                    val destination4 = document.getString("destination4")
                    val destination5 = document.getString("destination4")
                    val optionTitle = document.getString("optionTitle")
                    val option1=document.getString("option1")
                    val option2=document.getString("option2")
                    val option3=document.getString("option3")
                    val preTourImgUrl = document.getString("preTourImgUrl")

                    findViewById<TextView>(R.id.tourdetailspgtitle).text =tourTitle
                    findViewById<TextView>(R.id.tourdetailspgfaci).text =tourFacilities
                    findViewById<TextView>(R.id.tourdetailspgprice).text =tourPrice
                    findViewById<TextView>(R.id.destinationInput1).text = destination1
                    findViewById<TextView>(R.id.destinationInput2).text = destination2
                    findViewById<TextView>(R.id.destinationInput3).text = destination3
                    findViewById<TextView>(R.id.destinationInput4).text = destination4
                    findViewById<TextView>(R.id.destinationInput5).text = destination5
                    findViewById<TextView>(R.id.bpoptionTitle).text =optionTitle
                    findViewById<TextView>(R.id.bpoption1).text =option1
                    findViewById<TextView>(R.id.bpoption2).text = option2
                    findViewById<TextView>(R.id.bpoption3).text = option3

                    val preTourCoverImg = findViewById<ImageView>(R.id.tourdetailspgcoverimg)
                    if (!preTourImgUrl.isNullOrEmpty()) {
                        Glide.with(this)
                            .load(preTourImgUrl)  // load the image URL from Firebase
                            .into(preTourCoverImg) // display the image in ImageView
                    } else {
                        preTourCoverImg.setImageResource(R.drawable.img_defaultimage)  // set default image if URL is null or empty
                    }

                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error getting hotel details: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

