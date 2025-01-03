package com.example.tourio

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PredefinedTourAdapter(
    private val preDefTourList: List<PredefinedTour>,
    private val onTourClick: (String) -> Unit // Callback for click handling
) : RecyclerView.Adapter<PredefinedTourAdapter.TourViewHolder>() {

    inner class TourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val preDefTourTitleTextView: TextView = itemView.findViewById(R.id.preDefTourCardTitle)
        val preDefTourDestinationTextView: TextView = itemView.findViewById(R.id.preDefTourCardDes)
        val preDefTourPriceTextView: TextView = itemView.findViewById(R.id.preDefTourCardPrice)
        val preDefTourImageView: ImageView = itemView.findViewById(R.id.preDefTourCardImage)

        init {
            // handle the card view click event
            itemView.setOnClickListener {
                val preDefTourId = preDefTourList[adapterPosition].preDefTourId
                onTourClick(preDefTourId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tour_card_item, parent, false)
        return TourViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TourViewHolder, position: Int) {
        val preDefTour = preDefTourList[position]
        holder.preDefTourTitleTextView.text = preDefTour.tourTitle
        holder.preDefTourDestinationTextView.text = preDefTour.destination1
        holder.preDefTourPriceTextView.text = preDefTour.tourPrice

        val preDefTourImgUrl = preDefTour.preTourImgUrl
        if (preDefTourImgUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(preDefTourImgUrl)  // Load image from Firebase Storage URL
                .into(holder.preDefTourImageView)  // Set the image in ImageView
        } else {
            holder.preDefTourImageView.setImageResource(R.drawable.img_sigiriya_1) // Set a default image if no URL is available
        }
    }

    override fun getItemCount(): Int {
        return preDefTourList.size
    }
}
