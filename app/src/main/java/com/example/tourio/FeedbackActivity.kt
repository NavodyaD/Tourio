package com.example.feedbackform

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tourio.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FeedbackActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var feedbackEditText: EditText
    private lateinit var sendFeedbackButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        // Initialize views
        ratingBar = findViewById(R.id.RatingBar)
        feedbackEditText = findViewById(R.id.review)
        sendFeedbackButton = findViewById(R.id.sendFeedbackButton1)


        sendFeedbackButton.setOnClickListener {

            // Get user inputs
            val rating = ratingBar.rating
            val feedback = feedbackEditText.text.toString().trim()

            // Validate inputs
            if (feedback.isEmpty())
            {
                Toast.makeText(this, "Please write your feedback.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Send feedback to Firebase
            sendFeedback(rating, feedback)
        }
    }

    private fun sendFeedback(rating: Float, feedback: String) {
        val db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null)
        {
            val userId = currentUser.uid

            // Create a map to store feedback details
            val feedbackData = hashMapOf(
                "userId" to userId,
                "rating" to rating,
                "feedback" to feedback,
                "timestamp" to System.currentTimeMillis() // Add a timestamp
            )

            db.collection("feedback")
                .add(feedbackData) // Generates a document ID automatically
                .addOnSuccessListener {
                    Toast.makeText(this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show()

                    // Clear the inputs
                    ratingBar.rating = 0f
                    feedbackEditText.text.clear()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to send feedback: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
        else
        {
            // If user is not logged in
            Toast.makeText(this, "Cannot identify the user.", Toast.LENGTH_SHORT).show()
        }
    }
}
