package com.example.tourio

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class RequestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflater.inflate(R.layout.activity_tourrequestpage, container, false)

        // Find the button in the inflated layout
        val newReqButton: Button = binding.findViewById(R.id.newRequestButton)

        // Set click listener for the button
        newReqButton.setOnClickListener {
            // Navigate to NewRequestActivity
            val intent = Intent(activity, UserToursRequestFormActivity::class.java)
            startActivity(intent)
        }

        return binding
    }
}
