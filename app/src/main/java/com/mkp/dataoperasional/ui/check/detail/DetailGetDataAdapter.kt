package com.mkp.dataoperasional.ui.check.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mkp.dataoperasional.data.response.ItemsItem
import com.mkp.dataoperasional.databinding.ListItemDetailBinding

class DetailGetDataAdapter : ListAdapter<ItemsItem, DetailGetDataAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemsItem = getItem(position)
        holder.bind(itemsItem)
    }

    inner class MyViewHolder(private val binding: ListItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ItemsItem) {
            binding.textViewSize.text = item.size
            binding.textViewStock.text = "Stock : ${item.stock}"
            // Uncomment and adjust if you want to load an image
            // Glide.with(binding.root).load(item.imageUrl).into(binding.imageView)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem.size == newItem.size // Assuming size is unique; adjust if needed
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem // Use equals method if properly overridden in ItemsItem
            }
        }
    }
}
