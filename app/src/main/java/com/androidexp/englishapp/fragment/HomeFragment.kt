package com.androidexp.englishapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidexp.englishapp.activity.CategoryMealsActivity
import com.androidexp.englishapp.MainActivity
import com.androidexp.englishapp.activity.MealActivity
import com.androidexp.englishapp.adapter.CategoriesAdapter
import com.androidexp.englishapp.adapter.MostPopularAdapter
import com.androidexp.englishapp.databinding.FragmentHomeBinding
import com.androidexp.englishapp.model.MealsByCategory
import com.androidexp.englishapp.model.Meal
import com.androidexp.englishapp.videoModel.HomeViewModel
import com.bumptech.glide.Glide


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private var randomMeal: Meal? =null

    private lateinit var popularItemsAdapter:MostPopularAdapter
    private lateinit var categoriesAdapter:CategoriesAdapter

    companion object{
        const val MEAL_ID = "com.androidexp.englishapp.fragment.idMeal"
        const val MEAL_NAME = "com.androidexp.englishapp.fragment.nameMeal"
        const val MEAL_THUMB = "com.androidexp.englishapp.fragment.thumbMeal"

        const val CATEGORY_NAME = "com.androidexp.englishapp.fragment.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        popularItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItemsLivedata()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        viewModel.getCategories()
        observeCategoriesLiveData()

        onCategoryClick()

    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewCategory.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner,Observer{ categories->
            categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = {meal->
            var intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLivedata() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner,
            { mealList->
                popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)

            })
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener{
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal!!.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal!!.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal!!.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLivedata().observe(viewLifecycleOwner, Observer { meal ->
            Glide.with(this@HomeFragment)
                .load(meal.strMealThumb)
                .into(binding.imgRandomMeal)

            randomMeal = meal
        })
    }
}