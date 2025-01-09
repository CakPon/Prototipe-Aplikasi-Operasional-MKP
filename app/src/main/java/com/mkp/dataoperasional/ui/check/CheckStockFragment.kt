package com.mkp.dataoperasional.ui.check

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mkp.dataoperasional.data.ResultState
import com.mkp.dataoperasional.ViewModelFactory
import com.mkp.dataoperasional.databinding.FragmentCheckStockBinding

class CheckStockFragment : Fragment() {
    private var _binding: FragmentCheckStockBinding? = null
    private val binding get() = _binding!!
    private lateinit var getDataExpandableListAdapter: GetDataExpandableListAdapter
    private val getDataViewModel by viewModels<GetDataViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckStockBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener {
            displayClothesWearPack()
        }

        displayClothesWearPack()
    }

    private fun displayClothesWearPack() {
        showLoading(true)
        getDataViewModel.getBajuWearPack().observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {
                    showLoading(true)
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                is ResultState.Success -> {
                    getDataExpandableListAdapter = GetDataExpandableListAdapter(result.data.categories, requireContext())
                    binding.expandibleListview.setAdapter(getDataExpandableListAdapter)
                    showLoading(false)
                    binding.swipeRefreshLayout.isRefreshing = false
                    showToast("Data loaded successfully")
                }
                is ResultState.Error -> {
                    showLoading(false)
                    binding.swipeRefreshLayout.isRefreshing = false // stop refreshing
                    showToast("Failed to load data")
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.expandibleListview.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
