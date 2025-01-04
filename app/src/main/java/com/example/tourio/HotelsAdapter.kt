package com.example.tourio

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HotelsAdapter(
    private val hotelList: List<Hotel>,
    private val onHotelClick: (String) -> Unit   // Callback for click handling
) : RecyclerView.Adapter<HotelsAdapter.HotelViewHolder>() {

    inner class HotelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hotelNameTextView: TextView = itemView.findViewById(R.id.hotelName)
        val hotelAddressTextView: TextView = itemView.findViewById(R.id.hotelAddress)
        val hotelCardImageView: ImageView = itemView.findViewById(R.id.hotelCardBGImage)

        init {
            // handle the card view click event
            itemView.setOnClickListener {
                val userId = hotelList[adapterPosition].userId
                onHotelClick(userId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.hotel_card_item, parent, false)
        return HotelViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val hotel = hotelList[position]
        holder.hotelNameTextView.text = hotel.hotelName
        holder.hotelAddressTextView.text = hotel.hotelAddress

        val hotelCardImgUrl = hotel.hotelCoverImgURL
        if (hotelCardImgUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(hotelCardImgUrl)  // Load image from Firebase Storage URL
                .into(holder.hotelCardImageView)  // Set the image in ImageView
        } else {
            holder.hotelCardImageView.setImageResource(R.drawable.img_sigiriya_1) // Set a default image if no URL is available
        }
    }

    override fun getItemCount(): Int {
        return hotelList.size
    }
}
