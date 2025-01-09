package com.mkp.dataoperasional.ui.check.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkp.dataoperasional.ViewModelFactory
import com.mkp.dataoperasional.data.ResultState
import com.mkp.dataoperasional.databinding.ActivityDetailGetDataBinding
import com.mkp.dataoperasional.ui.check.GetDataViewModel

@Suppress("DEPRECATION")
class DetailGetDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGetDataBinding
    private lateinit var detailGetDataAdapter: DetailGetDataAdapter
    private val getDataViewModel by viewModels<GetDataViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGetDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("CATEGORY")


        val category = intent.getStringExtra("CATEGORY") ?: ""
        val color = intent.getStringExtra("COLOR") ?: ""

        binding.textViewColor.text = intent.getStringExtra("COLOR")

        detailGetDataAdapter = DetailGetDataAdapter()
        binding.rvDetail.layoutManager = LinearLayoutManager(this)
        binding.rvDetail.adapter = detailGetDataAdapter

        displayClothesWearPack(category, color)
    }

    private fun displayClothesWearPack(category: String, color: String) {
        getDataViewModel.getBajuWearPack().observe(this) { result ->
            showLoading(true)
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                }
                is ResultState.Success -> {

                    val filteredItems = result.data.categories
                        .filter { it.category == category }
                        .flatMap { it.items }
                        .filter { it.color == color }
                        .flatMap { it.items }

                    detailGetDataAdapter.submitList(filteredItems)
                    showToast("Success to fetch data")
                    showLoading(false)
                }
                is ResultState.Error -> {
                    showToast("Failed to fetch data")
                    showLoading(false)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
