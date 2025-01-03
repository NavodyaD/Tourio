package com.example.tourio

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserToursRequestFormActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usertoursrequestform)

        val continueButton = findViewById<Button>(R.id.buttonPost)
        continueButton.setOnClickListener {
            addUserTourRequests()
        }
    }

    private fun addUserTourRequests() {
        val db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            // Get data from EditText fields
            val tourTitle = findViewById<EditText>(R.id.tourTitle).text.toString()
            val destination1 = findViewById<EditText>(R.id.destination1).text.toString()
            val des1MapUrl = findViewById<EditText>(R.id.des1MapUrl).text.toString()
            val destination2= findViewById<EditText>(R.id.destination2).text.toString()
            val des2MapUrl= findViewById<EditText>(R.id.des2MapUrl).text.toString()
            val destination3= findViewById<EditText>(R.id.destination3).text.toString()
            val des3MapUrl= findViewById<EditText>(R.id.des3MapUrl).text.toString()
            val destination4= findViewById<EditText>(R.id.destination4).text.toString()
            val des4MapUrl= findViewById<EditText>(R.id.des4MapUrl).text.toString()
            val destination5 = findViewById<EditText>(R.id.destination5).text.toString()
            val des5MapUrl = findViewById<EditText>(R.id.des5MapUrl).text.toString()
            val acceptedBudget= findViewById<EditText>(R.id.acceptedBudget).text.toString()
            val specialNotes = findViewById<EditText>(R.id.specialNotes).text.toString()
            val buttonPost = findViewById<EditText>(R.id.buttonPost).text.toString()


            if (tourTitle.isEmpty() || destination1.isEmpty() || des1MapUrl.isEmpty() || destination2.isEmpty() || des2MapUrl.isEmpty() || destination3.isEmpty()
                || des3MapUrl.isEmpty() || des3MapUrl.isEmpty() || destination4.isEmpty() || des4MapUrl.isEmpty() || destination5.isEmpty() || des5MapUrl.isEmpty()
                || acceptedBudget.isEmpty() || specialNotes.isEmpty() || buttonPost.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
                return
            }

            // Create a map to store the preDefined tours data
            val tourData = hashMapOf(
                "destination1" to destination1,
                "des1MapUrl" to des1MapUrl,
                "destination2" to destination2,
                "des2MapUrl" to des2MapUrl,
                "destination3" to destination3,
                "des3MapUrl" to des3MapUrl,
                "destination4" to destination4,
                "des4MapUrl" to des4MapUrl,
                "destination5" to destination5,
                "des5MapUrl" to des5MapUrl,
                "acceptedBudget" to acceptedBudget,
                "specialNotes" to specialNotes,
                "buttonPost" to buttonPost// Associate with current user's ID
            )

            // Add data to the 'preDefinedTours' collection
            db.collection("userTourRequest")
                .add(tourData) // Automatically generates a document ID
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Your tour request has been submitted successfully!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Tour request submission failed. Please try again: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            // If user is not logged in
            Toast.makeText(this, "Cannot Identify the User", Toast.LENGTH_SHORT).show()
        }
    }
}