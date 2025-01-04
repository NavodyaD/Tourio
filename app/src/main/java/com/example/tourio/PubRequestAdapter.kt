package com.example.tourio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PubRequestAdapter(
    private val pubRequestsList: List<PubRequest>,
    function: () -> Unit,
) : RecyclerView.Adapter<PubRequestAdapter.PubRequestViewHolder>() {

    inner class PubRequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reqTitleTextView: TextView = itemView.findViewById(R.id.pubReqTitle)
        val reqDes1TextView: TextView = itemView.findViewById(R.id.pubReqDes1)
        val reqDes2TextView: TextView = itemView.findViewById(R.id.pubReqDes2)
        val reqBudgetTextView: TextView = itemView.findViewById(R.id.pubReqBudget)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PubRequestViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.request_card_item, parent, false)
        return PubRequestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PubRequestViewHolder, position: Int) {
        val pubRequest = pubRequestsList[position]
        holder.reqTitleTextView.text = pubRequest.tourTitle
        holder.reqDes1TextView.text = pubRequest.destination1
        holder.reqDes2TextView.text = pubRequest.destination2
        holder.reqBudgetTextView.text = pubRequest.acceptedBudget

    }

    override fun getItemCount(): Int {
        return pubRequestsList.size
    }
}
