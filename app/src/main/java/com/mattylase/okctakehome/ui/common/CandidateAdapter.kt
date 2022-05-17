package com.mattylase.okctakehome.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mattylase.okctakehome.R
import com.mattylase.okctakehome.ui.common.model.Candidate

class CandidateAdapter : RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder>() {
    private var onItemClick: ((Int, String) -> Unit)? = null
    private var items: MutableList<Candidate> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_candidate, parent, false)

        return CandidateViewHolder(view)
    }

    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    /**
     * Update the entire list. Used for initial load & population of the Match % screen
     */
    fun updateAllCandidates(items: List<Candidate>) {
        this.items.clear()
        this.items.addAll(items)
        notifyItemRangeChanged(0, items.size)
    }

    /**
     * Update a single candidate after it was liked
     */
    fun updateCandidate(position: Int, candidate: Candidate) {
        if (position in items.indices) {
            items[position] = candidate
            notifyItemChanged(position)
        }
    }

    fun setOnClickListener(listener: (Int, String) -> Unit) {
        onItemClick = listener
    }

    inner class CandidateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(candidate: Candidate) {
            itemView.findViewById<TextView>(R.id.candidateUserName).text = candidate.username
            itemView.findViewById<TextView>(R.id.candidateMatchPercentage).run {
                // assuming the number string returned from the API is actually a decimal at
                // 2 digits of precision - just use the base decimal with no rounding
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
                    .placeholder(R.drawable.paw_placeholder)
                    .error(R.drawable.not_found_error)
                    .centerCrop()
                    .into(this)
            }

            updateCardBackground(candidate.liked)
            itemView.setOnClickListener {
                onItemClick?.invoke(adapterPosition, items[adapterPosition].id)
            }
        }

        private fun updateCardBackground(liked: Boolean) {
            with(itemView as CardView) {
                val color = if (liked) {
                    ResourcesCompat.getColor(context.resources, R.color.yellow, null)
                } else {
                    ResourcesCompat.getColor(context.resources, R.color.white, null)
                }
                setCardBackgroundColor(color)
            }
        }
    }
}
