package com.example.tourio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class HomePageActivity : AppCompatActivity() {

    private lateinit var adapter: PredefinedTourAdapter
    private val preDefTourList = mutableListOf<PredefinedTour>()

    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val recyclerView = findViewById<RecyclerView>(R.id.homePreDefToursRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PredefinedTourAdapter(preDefTourList) { preDefTourId ->
            // navigate user to hotel details page with userId
            val intent = Intent(this, BookTourActivity::class.java)
            intent.putExtra("preDefTourId", preDefTourId)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        fetchPreDefToursFromFirestore()
    }

    private fun fetchPreDefToursFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("PredefinedTours").get()
            .addOnSuccessListener { result ->
                preDefTourList.clear()
                for (document in result) {
                    val preDefTour = document.toObject(PredefinedTour::class.java)
                    preDefTourList.add(preDefTour)
                }
                adapter.notifyDataSetChanged()
            }
    }
}