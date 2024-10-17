package com.androidexp.englishapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidexp.englishapp.MainActivity
import com.androidexp.englishapp.adapter.MealsAdapter
import com.androidexp.englishapp.databinding.FragmentSearchBinding
import com.androidexp.englishapp.model.Meal
import com.androidexp.englishapp.videoModel.HomeViewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchRecyclerviewAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()

        binding.btnSearchArrow.setOnClickListener { searchMeals() }

        observeSearchedMealsLiveData()
    }

    private fun observeSearchedMealsLiveData() {
        viewModel.observeSearchedMealsLiveData().observe(viewLifecycleOwner, Observer { mealsList ->
            if (mealsList != null && mealsList.isNotEmpty()) {
                searchRecyclerviewAdapter.differ.submitList(mealsList)
            } else {
                Log.d("SearchFragment", "No meals found")
            }
        })

    }

    private fun searchMeals() {
        val searchQuery = binding.edSearchBox.text.toString()
        if (searchQuery.isNotEmpty()) {
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchRecyclerviewAdapter = MealsAdapter()
        binding.rvSearchedMeals.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchRecyclerviewAdapter
        }
    }
}
