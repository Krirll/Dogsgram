package ru.krirll.dogsgram.presentation.activity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.load
import ru.krirll.dogsgram.R
import ru.krirll.dogsgram.databinding.ItemLayoutBinding
import ru.krirll.dogsgram.domain.model.Dog

class RecyclerViewAdapter : ListAdapter<Dog, ItemViewHolder>(ItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(layoutInflater)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            userImageView.load(currentList[position].url)
            dogImageView.load(currentList[position].url) {
                placeholder(R.drawable.corgi_placeholder)
            }
            moreButton.setOnClickListener {
                it.visibility = View.GONE
                descriptionTextView.maxLines = Int.MAX_VALUE
            }
        }
    }
}