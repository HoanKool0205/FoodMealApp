package com.androidexp.englishapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidexp.englishapp.MainActivity
import com.androidexp.englishapp.activity.MealActivity
import com.androidexp.englishapp.adapter.SearchMealAdapter
import com.androidexp.englishapp.databinding.FragmentSearchBinding
import com.androidexp.englishapp.fragment.HomeFragment.Companion.MEAL_ID
import com.androidexp.englishapp.fragment.HomeFragment.Companion.MEAL_NAME
import com.androidexp.englishapp.fragment.HomeFragment.Companion.MEAL_THUMB
import com.androidexp.englishapp.videoModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchRecyclerviewAdapter: SearchMealAdapter

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

        var searchJob: Job? = null
        binding.edSearchBox.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(200)
                if (searchQuery.toString().isNotEmpty()) {
                    viewModel.searchMeals(searchQuery.toString())
                }
            }
        }
    }

    private fun observeSearchedMealsLiveData() {
        viewModel.observeSearchedMealsLiveData().observe(viewLifecycleOwner, Observer { mealsList ->
            if (mealsList != null && mealsList.isNotEmpty()) {
                searchRecyclerviewAdapter.setSearchMeals(mealsList as ArrayList)
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
        searchRecyclerviewAdapter = SearchMealAdapter().apply {
            onItemClick = { meal ->
                val intent = Intent(activity, MealActivity::class.java).apply {
                    putExtra(MEAL_ID, meal.idMeal)
                    putExtra(MEAL_NAME, meal.strMeal)
                    putExtra(MEAL_THUMB, meal.strMealThumb)
                }
                startActivity(intent)
            }
        }
        binding.rvSearchedMeals.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchRecyclerviewAdapter
        }
    }
}
