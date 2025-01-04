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

        val userId = intent.getStringExtra("preDefTourId") ?: return
        fetchBookDetails(userId)
    }
    private fun fetchBookDetails(userId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("PredefinedTours").whereEqualTo("preDefTourId", userId).get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    // get firebase field data to values
                    val document = result.documents[0]
                    val destination1 = document.getString("destination1")
                    val destination2 = document.getString("destination2")
                    val destination3 = document.getString("destination3")
                    val destination4 = document.getString("destination4")
                    val destination5 = document.getString("destination4")
                    val option1=document.getString("option1")
                    val option2=document.getString("option2")
                    val option3=document.getString("option3")

                    findViewById<TextView>(R.id.destinationInput1).text = destination1
                    findViewById<TextView>(R.id.destinationInput2).text = destination2
                    findViewById<TextView>(R.id.destinationInput3).text = destination3
                    findViewById<TextView>(R.id.option1).text =option1
                    findViewById<TextView>(R.id.option2).text = option2
                    findViewById<TextView>(R.id.option3).text = option3

                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error getting hotel details: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

