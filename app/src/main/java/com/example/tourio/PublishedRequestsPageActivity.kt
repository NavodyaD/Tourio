package com.example.tourio

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class PublishedRequestsPageActivity : AppCompatActivity() {

    private lateinit var adapter: PubRequestAdapter
    private val pubRequestsList = mutableListOf<PubRequest>()

    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publishedrequestspage)

        val userId = intent.getStringExtra("userId") ?: return

        val recyclerView = findViewById<RecyclerView>(R.id.pubreqrecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = PubRequestAdapter(pubRequestsList) {

        }

        recyclerView.adapter = adapter

        fetchRequestsFromFirestore()

    }

    private fun fetchRequestsFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("TourRequests").get()
            .addOnSuccessListener { result ->
                pubRequestsList.clear()
                for (document in result) {
                    val pubRequest = document.toObject(PubRequest::class.java)
                    pubRequestsList.add(pubRequest)
                }
                adapter.notifyDataSetChanged()
            }
    }
}