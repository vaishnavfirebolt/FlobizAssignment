package com.vaishnav.flobizassignment.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vaishnav.flobizassignment.api.models.DataResponseItem
import com.vaishnav.flobizassignment.databinding.ItemBinding

class Adapter : ListAdapter<DataResponseItem, Adapter.AViewHolder>(DataComparator()) {
    inner class AViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class DataComparator : DiffUtil.ItemCallback<DataResponseItem>() {
        override fun areContentsTheSame(oldItem: DataResponseItem, newItem: DataResponseItem) =
            oldItem.toString() == newItem.toString()

        override fun areItemsTheSame(oldItem: DataResponseItem, newItem: DataResponseItem) =
            oldItem === newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AViewHolder {
        val binding =
            ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AViewHolder, position: Int) {
        ItemBinding.bind(holder.itemView).apply {
            val currentData = getItem(position)

            tvTitle.text = currentData.title
            Glide.with(ivPhoto).load(currentData.url).into(ivPhoto)
        }
    }
}