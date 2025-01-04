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

        val continueButton = findViewById<Button>(R.id.tourReqPostButton)
        continueButton.setOnClickListener {
            addUserTourRequest()
        }
    }

    private fun addUserTourRequest() {
        val db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            // Get data from EditText fields
            val tourReqTitle = findViewById<EditText>(R.id.tourReqTitle).text.toString()
            val tourReqestination1 = findViewById<EditText>(R.id.tourReqDestination1).text.toString()
            val tourReqdes1MapUrl = findViewById<EditText>(R.id.tourReqDes1MapUrl).text.toString()
            val tourReqdestination2= findViewById<EditText>(R.id.tourReqDestination2).text.toString()
            val tourReqdes2MapUrl= findViewById<EditText>(R.id.tourReqDes2MapUrl).text.toString()
            val tourReqdestination3= findViewById<EditText>(R.id.tourReqDestination3).text.toString()
            val tourReqdes3MapUrl= findViewById<EditText>(R.id.tourReqDes3MapUrl).text.toString()
            val tourReqdestination4= findViewById<EditText>(R.id.tourReqDestination4).text.toString()
            val tourReqdes4MapUrl= findViewById<EditText>(R.id.tourReqDes4MapUrl).text.toString()
            val tourReqdestination5 = findViewById<EditText>(R.id.tourReqDestination5).text.toString()
            val tourReqdes5MapUrl = findViewById<EditText>(R.id.tourReqDes5MapUrl).text.toString()
            val tourReqBudget= findViewById<EditText>(R.id.tourReqBudget).text.toString()
            val tourReqNotes = findViewById<EditText>(R.id.tourReqNotes).text.toString()


            if (tourReqTitle.isEmpty() || tourReqestination1.isEmpty() || tourReqdes1MapUrl.isEmpty() || tourReqdestination2.isEmpty() || tourReqdes2MapUrl.isEmpty() || tourReqdestination3.isEmpty()
                || tourReqdes3MapUrl.isEmpty() || tourReqdestination4.isEmpty() || tourReqdes4MapUrl.isEmpty() || tourReqdestination5.isEmpty() || tourReqdes5MapUrl.isEmpty()
                || tourReqBudget.isEmpty() || tourReqNotes.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
                return
            }

            // Create a map to store the preDefined tours data
            val tourReqData = hashMapOf(
                "userId" to userId,
                "tourTitle" to tourReqTitle,
                "destination1" to tourReqestination1,
                "des1MapUrl" to tourReqdes1MapUrl,
                "destination2" to tourReqdestination2,
                "des2MapUrl" to tourReqdes2MapUrl,
                "destination3" to tourReqdestination3,
                "des3MapUrl" to tourReqdes3MapUrl,
                "destination4" to tourReqdestination4,
                "des4MapUrl" to tourReqdes4MapUrl,
                "destination5" to tourReqdestination5,
                "des5MapUrl" to tourReqdes5MapUrl,
                "acceptedBudget" to tourReqBudget,
                "specialNotes" to tourReqNotes
            )

            // Add tour request data to the 'TourRequests' collection
            db.collection("TourRequests")
                .add(tourReqData) // This automatically generates a document ID
                .addOnSuccessListener { documentReference ->
                    // Get the generated document ID
                    val generatedTourReqId = documentReference.id

                    // Update the tourData map to include the generated tourId
                    val updatedTourReqData = tourReqData.toMutableMap()
                    updatedTourReqData["tourReqId"] = generatedTourReqId

                    // Now, update the Firestore document with the tourReqId field
                    documentReference.set(updatedTourReqData) // Overwrite the document with the updated data
                        .addOnSuccessListener {
                            Toast.makeText(this, "Your tour request published successfully!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
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