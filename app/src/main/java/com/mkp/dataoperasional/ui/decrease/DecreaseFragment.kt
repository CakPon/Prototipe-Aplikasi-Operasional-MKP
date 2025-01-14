package com.mkp.dataoperasional.ui.decrease

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mkp.dataoperasional.data.ResultState
import com.mkp.dataoperasional.ViewModelFactory
import com.mkp.dataoperasional.databinding.FragmentDecreaseBinding

class DecreaseFragment : Fragment() {

    private var _binding: FragmentDecreaseBinding? = null
    private val binding get() = _binding!!
    private lateinit var decreaseAdapter: DecreaseAdapter
    private val decreaseViewModel by viewModels<DecreaseViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDecreaseBinding.inflate(inflater, container, false)
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
        decreaseViewModel.getData().observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                is ResultState.Success -> {
                    // Create the adapter and set it to the ExpandableListView
                    decreaseAdapter = DecreaseAdapter(result.data.categories, requireContext()) // Pass context here
                    binding.elDecrease.setAdapter(decreaseAdapter)
                    showLoading(false)
                    binding.swipeRefreshLayout.isRefreshing = false
                    showToast("Data loaded successfully") // Show success toast
                }
                is ResultState.Error -> {
                    showLoading(false)
                    binding.swipeRefreshLayout.isRefreshing = false
                    showToast("Failed to load data") // Show error toast with message
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.elDecrease.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
