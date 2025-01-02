package com.example.wordsfactory.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsfactory.model.Definition
import com.example.wordsfactory.R

class MeaningAdapter(
    private val meanings : List<Definition>
) : RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {
    class MeaningViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val definitionTextView: TextView = view.findViewById(R.id.definitionTextView)
        val exampleTextView: TextView = view.findViewById(R.id.exampleTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meaning, parent, false)
        return MeaningViewHolder(view)
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        val meaning = meanings[position]
        holder.definitionTextView.text = meaning.definition
        holder.exampleTextView.text = meaning.example ?: "No example available"
    }

    override fun getItemCount(): Int = meanings.size
}