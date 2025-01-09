package com.mkp.dataoperasional.ui.restock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mkp.dataoperasional.data.ResultState
import com.mkp.dataoperasional.ViewModelFactory
import com.mkp.dataoperasional.databinding.FragmentRestockBinding

class RestockFragment : Fragment() {

    private var _binding: FragmentRestockBinding? = null
    private val binding get() = _binding!!
    private lateinit var restockAdapter: RestockAdapter
    private val restockViewModel by viewModels<RestockViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.setOnRefreshListener {
            displayGetData()
        }

        displayGetData()
    }

    private fun displayGetData() {
        showLoading(true)
        restockViewModel.getData().observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                is ResultState.Success -> {
                    restockAdapter = RestockAdapter(result.data.categories, requireContext()) // Pass context here
                    binding.elRestock.setAdapter(restockAdapter)
                    showToast("Data loaded successfully")
                    binding.swipeRefreshLayout.isRefreshing = false
                    showLoading(false)
                }
                is ResultState.Error -> {
                    showToast("Failed to load data")
                    binding.swipeRefreshLayout.isRefreshing = false
                    showLoading(false)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.elRestock.visibility = if (isLoading) View.GONE else View.VISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
