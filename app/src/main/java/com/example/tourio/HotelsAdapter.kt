package com.example.tourio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HotelsAdapter(private val hotelList: List<Hotel>) : RecyclerView.Adapter<HotelsAdapter.HotelViewHolder>() {

    inner class HotelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hotelNameTextView: TextView = itemView.findViewById(R.id.hotelName)
        val hotelAddressTextView: TextView = itemView.findViewById(R.id.hotelAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.hotel_card_item, parent, false)
        return HotelViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val hotel = hotelList[position]
        holder.hotelNameTextView.text = hotel.hotelName
        holder.hotelAddressTextView.text = hotel.hotelAddress
    }

    override fun getItemCount(): Int {
        return hotelList.size
    }
}