package com.example.tourio

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class HotelsPageActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HotelsAdapter
    private val hotelsList = mutableListOf<Hotel>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotelspage)

        recyclerView = findViewById(R.id.myrecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = HotelsAdapter(hotelsList)
        recyclerView.adapter = adapter

        fetchHotelsFromFirestore()

    }

    private fun fetchHotelsFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("Hotels").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val hotel = document.toObject(Hotel::class.java)
                    hotelsList.add(hotel)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                // Handle errors
            }
    }
}