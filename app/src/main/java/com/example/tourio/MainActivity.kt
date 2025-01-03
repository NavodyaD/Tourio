package com.example.tourio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: HotelsAdapter
    private val hotelsList = mutableListOf<Hotel>()

    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotelspage)

        val recyclerView = findViewById<RecyclerView>(R.id.myrecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = HotelsAdapter(hotelsList) { userId ->
            // navigate user to hotel details page with userId
            val intent = Intent(this, HotelProfileUserViewActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        fetchHotelsFromFirestore()

        //super.onCreate(savedInstanceState)

        //val getStartedButton: Button = findViewById(R.id.getstarted_button)

        //getStartedButton.setOnClickListener {
            //val intent = Intent(this, SignUpActivity::class.java)
            //startActivity(intent)
        //}
        // -------------------
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.feedbackpage)) { v, insets ->
        //val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        //insets
        //}
    }

    private fun fetchHotelsFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("Hotels").get()
            .addOnSuccessListener { result ->
                hotelsList.clear()
                for (document in result) {
                    val hotel = document.toObject(Hotel::class.java)
                    hotelsList.add(hotel)
                }
                adapter.notifyDataSetChanged()
            }
    }

    fun navigateToLogin(view: View) {}
}
