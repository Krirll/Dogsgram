package ru.krirll.dogsgram.presentation.activity.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.krirll.dogsgram.domain.model.Dog

class ItemDiffUtil: DiffUtil.ItemCallback<Dog>() {

    override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
        return oldItem.url == newItem.url
    }
}