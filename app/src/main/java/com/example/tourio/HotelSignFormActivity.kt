package com.example.tourio

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class HotelSignFormActivity : AppCompatActivity() {

    private lateinit var hotelCoverImgView: ImageView
    private var selectedImageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotelsignform)

        hotelCoverImgView = findViewById(R.id.hotelImagePreview)

        val pickImageButton = findViewById<Button>(R.id.pickImageButton)
        val continueButton = findViewById<Button>(R.id.buttonContinue)

        pickImageButton.setOnClickListener {
            openImagePicker()
        }

        continueButton.setOnClickListener {
            if (selectedImageUri != null) {
                uploadImageToFirebase()
            } else {
                addHotelDetails("")
            }
        }
    }

    private fun openImagePicker() {
        // open the gallery to select an image
        val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            // set the URL of selected image to the imageView to preview
            selectedImageUri = data?.data
            hotelCoverImgView.setImageURI(selectedImageUri)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun uploadImageToFirebase() {
        val storageRef = FirebaseStorage.getInstance().reference.child("hotel_images/${UUID.randomUUID()}")

        storageRef.putFile(selectedImageUri!!)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    addHotelDetails(uri.toString())
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Image upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addHotelDetails(imageUrl: String) {
        val db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            // get text field data
            val hotelName = findViewById<EditText>(R.id.hotelName).text.toString()
            val hotelCustomID = findViewById<EditText>(R.id.hotelCustomID).text.toString()
            val hotelAddress = findViewById<EditText>(R.id.hotelAddress).text.toString()
            val hotelDescription = findViewById<EditText>(R.id.hotelDescription).text.toString()
            val hotelFacilities = findViewById<EditText>(R.id.hotelFacilities).text.toString()

            if (hotelName.isEmpty() || hotelCustomID.isEmpty() || hotelAddress.isEmpty() || hotelDescription.isEmpty() || hotelFacilities.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
                return
            }

            // create a map to store the hotel details
            val hotelData = hashMapOf(
                "hotelName" to hotelName,
                "hotelCustomID" to hotelCustomID,
                "hotelAddress" to hotelAddress,
                "hotelCoverImgURL" to imageUrl,
                "hotelDescription" to hotelDescription,
                "hotelFacilities" to hotelFacilities,
                "userId" to userId // current user's ID
            )

            db.collection("Hotels")
                .add(hotelData) // generates a document ID automatically
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this, "Welcome, $hotelName!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HotelDashBoardActivity::class.java)
                    intent.putExtra("userId", userId)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to setup hotel profile: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            // if user is not logged in
            Toast.makeText(this, "Cannot Identify the User", Toast.LENGTH_SHORT).show()
        }
    }
}
