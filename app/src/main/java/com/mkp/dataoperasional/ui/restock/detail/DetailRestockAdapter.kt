package com.mkp.dataoperasional.ui.restock.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mkp.dataoperasional.data.response.ItemsItem
import com.mkp.dataoperasional.databinding.ListDetailRestockAmbilBinding

class DetailRestockAdapter : ListAdapter<ItemsItem, DetailRestockAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListDetailRestockAmbilBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemsItem = getItem(position)
        holder.bind(itemsItem)
    }

    inner class MyViewHolder(private val binding: ListDetailRestockAmbilBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ItemsItem) {
            binding.textViewSize.text = item.size
            binding.editTextStock.setText(item.stock.toString())

            binding.buttonIncrease.setOnClickListener {
                var currentStock = item.stock
                currentStock += 1
                item.stock = currentStock
                binding.editTextStock.setText(currentStock.toString())
            }

            binding.buttonDecrease.setOnClickListener {
                var currentStock = item.stock
                if (currentStock > 0) {
                    currentStock -= 1
                    item.stock = currentStock
                    binding.editTextStock.setText(currentStock.toString())
                }
            }

            binding.editTextStock.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val inputText = binding.editTextStock.text.toString()
                    val newStock = inputText.toIntOrNull()
                    if (newStock != null) {
                        item.stock = newStock
                    } else {
                        binding.editTextStock.setText(item.stock.toString())
                    }
                }
            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem.size == newItem.size // Assuming size is unique
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem // Use equals if properly overridden in ItemsItem
            }
        }
    }
}
