package com.mkp.dataoperasional.ui.decrease.detail

import PostOneRowRequest
import PostRequest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkp.dataoperasional.ViewModelFactory
import com.mkp.dataoperasional.data.ResultState
import com.mkp.dataoperasional.data.response.ItemsItem
import com.mkp.dataoperasional.databinding.ActivityDetailDecreaseBinding
import com.mkp.dataoperasional.ui.decrease.DecreaseViewModel
import com.google.gson.Gson

class DetailDecreaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDecreaseBinding
    private lateinit var detailDecreaseAdapter: DetailDecreaseAdapter
    private val decreaseViewModel by viewModels<DecreaseViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var category: String
    private lateinit var color: String
    private lateinit var sizes: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDecreaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        category = intent.getStringExtra("CATEGORY").orEmpty()
        color = intent.getStringExtra("COLOR").orEmpty()
        sizes = intent.getStringArrayListExtra("SIZES") ?: arrayListOf()

        supportActionBar?.title = category

        val wearPackItems = sizes.map { size -> ItemsItem(size = size, stock = 0) }

        binding.textViewColor.text = color

        detailDecreaseAdapter = DetailDecreaseAdapter()
        binding.rvDetail.layoutManager = LinearLayoutManager(this)
        binding.rvDetail.adapter = detailDecreaseAdapter
        detailDecreaseAdapter.submitList(wearPackItems)

        binding.buttonDecrease.setOnClickListener {
            currentFocus?.clearFocus()
            if (category == "Baju Wear Pack") {
                postDataClothesWearPack()
            } else if (category == "Celana Wear Pack") {
                postDataPantsWearPack()
            } else if (category == "Helm dan Sepatu") {
                postDataThreeRowData()
            } else if (category == "Perlengkapan Lainnya") {
                postDataOneRowData()
            } else {
                showToast("Failed to post data")
            }
        }

    }

    private fun postDataClothesWearPack() {
        val updatedItems = detailDecreaseAdapter.currentList

        val targetStrings = updatedItems.map { it.size }
        val newStockArray = updatedItems.map { it.stock }

        val decreaseRequest = PostRequest(
            action = "decreaseClothesWearPack",
            category = color,
            targetStrings = targetStrings,
            newStockArray = newStockArray
        )
        val gson = Gson()
        val requestBodyJson = gson.toJson(decreaseRequest)
        Log.d("RequestBody", "Request Body: $requestBodyJson")

        decreaseViewModel.postDecreaseClothesWearPack(decreaseRequest).observe(this) { result ->
            showLoading(true)
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }

                is ResultState.Success -> {
                    showToast("Success to post data $category")
                    resetStockToZero(updatedItems)
                    showLoading(false)
                }

                is ResultState.Error -> {
                    showToast("Failed to post data")
                    showLoading(false)
                }
            }
        }
    }

    private fun postDataPantsWearPack() {
        val updatedItems = detailDecreaseAdapter.currentList

        val targetStrings = updatedItems.map { it.size }
        val newStockArray = updatedItems.map { it.stock }

        val decreaseRequest = PostRequest(
            action = "decreasePantsWearPack",
            category = color,
            targetStrings = targetStrings,
            newStockArray = newStockArray
        )
        val gson = Gson()
        val requestBodyJson = gson.toJson(decreaseRequest)
        Log.d("RequestBody", "Request Body: $requestBodyJson")

        decreaseViewModel.postDecreasePantsWearPack(decreaseRequest).observe(this) { result ->
            showLoading(true)
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }

                is ResultState.Success -> {
                    showToast("Success to post data $category")
                    resetStockToZero(updatedItems)
                    showLoading(false)
                }

                is ResultState.Error -> {
                    showToast("Failed to post data")
                    showLoading(false)
                }
            }
        }
    }

    private fun postDataThreeRowData() {
        val updatedItems = detailDecreaseAdapter.currentList

        val targetStrings = updatedItems.map { it.size }
        val newStockArray = updatedItems.map { it.stock }

        val decreaseRequest = PostRequest(
            action = "decreaseThreeRowData",
            category = color,
            targetStrings = targetStrings,
            newStockArray = newStockArray
        )
        val gson = Gson()
        val requestBodyJson = gson.toJson(decreaseRequest)
        Log.d("RequestBody", "Request Body: $requestBodyJson")

        decreaseViewModel.postDecreaseThreeRowData(decreaseRequest).observe(this) { result ->
            showLoading(true)
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }

                is ResultState.Success -> {
                    showToast("Success to post data $category")
                    resetStockToZero(updatedItems)
                    showLoading(false)
                }

                is ResultState.Error -> {
                    showToast("Failed to post data")
                    showLoading(false)
                }
            }
        }

    }

    private fun postDataOneRowData() {
        val updatedItems = detailDecreaseAdapter.currentList

        val newStock = updatedItems.firstOrNull()?.stock ?: 0
        val decreaseRequest = PostOneRowRequest(
            action = "decreaseOneRowData",
            targetCategory = color,
            newStock = newStock
        )
        val gson = Gson()
        val requestBodyJson = gson.toJson(decreaseRequest)
        Log.d("RequestBody", "Request Body: $requestBodyJson")

        decreaseViewModel.postDecreaseOneRowData(decreaseRequest).observe(this) { result ->
            showLoading(true)
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }

                is ResultState.Success -> {
                    showToast("Success to post data $category")
                    resetStockToZero(updatedItems)
                    showLoading(false)
                }

                is ResultState.Error -> {
                    showToast("Failed to post data")
                    showLoading(false)
                }
            }
        }
    }

    private fun resetStockToZero(updatedItems: List<ItemsItem>) {
        val resetItems = updatedItems.map { it.copy(stock = 0) }
        detailDecreaseAdapter.submitList(resetItems)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        intent.removeExtra("CATEGORY")
        intent.removeExtra("COLOR")
        intent.removeExtra("SIZES")
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
