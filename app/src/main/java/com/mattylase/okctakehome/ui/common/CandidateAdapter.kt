package com.mattylase.okctakehome.ui.common

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mattylase.okctakehome.R
import com.mattylase.okctakehome.ui.common.model.Candidate

class CandidateAdapter : RecyclerView.Adapter<CandidateViewHolder>() {

    var items: MutableList<Candidate> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_candidate, parent, false)

        return CandidateViewHolder(view)
    }

    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateCandidates(items: List<Candidate>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}

class CandidateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(candidate: Candidate) {
        itemView.findViewById<TextView>(R.id.candidateUserName).text = candidate.username
        itemView.findViewById<TextView>(R.id.candidateMatchPercentage).run {
            text = context.getString(R.string.text_match, candidate.matchPercentage.take(2))
        }
        itemView.findViewById<TextView>(R.id.candidateDetailsText).run {
            text = context.getString(
                R.string.text_details,
                candidate.age,
                candidate.city,
                candidate.state
            )
        }
        itemView.findViewById<ImageView>(R.id.candidateImage).run {
            Glide.with(this)
                .load(candidate.thumbUrl)
                .centerCrop()
                .into(this)
        }
    }
}