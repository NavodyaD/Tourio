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

class HotelFragment : Fragment() {

    private lateinit var adapter: HotelsAdapter
    private val hotelsList = mutableListOf<Hotel>()

    @SuppressLint("MissingInflatedId")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.activity_hotelspage, container, false)

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.myrecyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = HotelsAdapter(hotelsList) { userId ->
            // navigate user to hotel details page with userId
            val intent = Intent(activity, HotelProfileUserViewActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        fetchHotelsFromFirestore()

        return rootView
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
            }.addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error getting documents: $e", Toast.LENGTH_SHORT).show()
            }
    }
}
