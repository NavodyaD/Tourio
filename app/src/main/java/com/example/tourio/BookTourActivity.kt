package com.example.tourio

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import java.util.*

class BookTourActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booktour)

        val preDefTourId = intent.getStringExtra("preDefTourId") ?: return
        fetchBookDetails(preDefTourId)

        // Add the "Book Tour" button click listener
        val bookTourButton = findViewById<Button>(R.id.bookTourButton)
        bookTourButton.setOnClickListener {
            bookTour(preDefTourId)
        }
    }

    private fun fetchBookDetails(preDefTourId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("PredefinedTours").whereEqualTo("preDefTourId", preDefTourId).get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val document = result.documents[0]
                    val preDefTourId = document.getString("preDefTourId")
                    val hotelUserId = document.getString("hoteluserId")
                    val tourTitle = document.getString("tourTitle")
                    val tourFacilities = document.getString("facilities")
                    val tourPrice = document.getString("tourPrice")
                    val destination1 = document.getString("destination1")
                    val destination2 = document.getString("destination2")
                    val destination3 = document.getString("destination3")
                    val destination4 = document.getString("destination4")
                    val destination5 = document.getString("destination4")
                    val optionTitle = document.getString("optionTitle")
                    val option1 = document.getString("option1")
                    val option2 = document.getString("option2")
                    val option3 = document.getString("option3")
                    val preTourImgUrl = document.getString("preTourImgUrl")

                    findViewById<TextView>(R.id.tourdetailspgtitle).text = tourTitle
                    findViewById<TextView>(R.id.tourdetailspgfaci).text = tourFacilities
                    findViewById<TextView>(R.id.tourdetailspgprice).text = tourPrice
                    findViewById<TextView>(R.id.destinationInput1).text = destination1
                    findViewById<TextView>(R.id.destinationInput2).text = destination2
                    findViewById<TextView>(R.id.destinationInput3).text = destination3
                    findViewById<TextView>(R.id.destinationInput4).text = destination4
                    findViewById<TextView>(R.id.destinationInput5).text = destination5
                    findViewById<TextView>(R.id.bpoptionTitle).text = optionTitle
                    findViewById<TextView>(R.id.bpoption1).text = option1
                    findViewById<TextView>(R.id.bpoption2).text = option2
                    findViewById<TextView>(R.id.bpoption3).text = option3

                    val preTourCoverImg = findViewById<ImageView>(R.id.tourdetailspgcoverimg)
                    if (!preTourImgUrl.isNullOrEmpty()) {
                        Glide.with(this)
                            .load(preTourImgUrl)
                            .into(preTourCoverImg)
                    } else {
                        preTourCoverImg.setImageResource(R.drawable.img_defaultimage)
                    }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error getting tour details: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun bookTour(preDefTourId: String) {
        val db = FirebaseFirestore.getInstance()

        // Get details from the UI or fetched data
        val hotelUserId = intent.getStringExtra("hotelUserId") ?: return
        val tourTitle = findViewById<TextView>(R.id.tourdetailspgtitle).text.toString()
        val tourPrice = findViewById<TextView>(R.id.tourdetailspgprice).text.toString()

        // Generate a unique booking ID (you can use Firestore's document ID or a UUID)
        val tourBookingId = UUID.randomUUID().toString()

        // map of data to store in Firestore
        val bookingData = hashMapOf(
            "tourBookingId" to tourBookingId,
            "preDefTourId" to preDefTourId,
            //"hotelUserId" to hotelUserId,
            "tourTitle" to tourTitle,
            "tourPrice" to tourPrice,
            "bookingDate" to System.currentTimeMillis()
        )

        // save booking data to the "TourBookings" collection in Firestore
        db.collection("TourBookings").document(tourBookingId)
            .set(bookingData)
            .addOnSuccessListener {
                Toast.makeText(this, "Tour booked successfully!", Toast.LENGTH_SHORT).show()
                Log.d("BookTour", "Booking stored in Firestore with ID: $tourBookingId")
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error booking the tour: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("BookTour", "Error booking the tour", e)
            }
    }
}
