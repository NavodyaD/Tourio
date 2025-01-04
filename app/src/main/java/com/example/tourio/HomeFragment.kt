package com.example.tourio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private lateinit var adapter: PredefinedTourAdapter
    private val preDefTourList = mutableListOf<PredefinedTour>()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.activity_homepage, container, false)

        // Initialize the RecyclerView
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.homePreDefToursRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the adapter and set the item click listener
        adapter = PredefinedTourAdapter(preDefTourList) { preDefTourId ->
            // Handle item click and navigate to hotel details
            val intent = Intent(activity, BookTourActivity::class.java)
            intent.putExtra("preDefTourId", preDefTourId)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Fetch predefined tours from Firestore
        fetchPreDefToursFromFirestore()

        return rootView
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
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error getting documents: $e", Toast.LENGTH_SHORT).show()
            }
    }
}
