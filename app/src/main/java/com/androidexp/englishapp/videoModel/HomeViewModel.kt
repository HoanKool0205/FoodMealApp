package com.androidexp.englishapp.videoModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidexp.englishapp.db.MealDatabase
import com.androidexp.englishapp.model.Category
import com.androidexp.englishapp.model.CategoryList
import com.androidexp.englishapp.model.MealsByCategoryList
import com.androidexp.englishapp.model.MealsByCategory
import com.androidexp.englishapp.model.Meal
import com.androidexp.englishapp.model.MealList
import com.androidexp.englishapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDatabase: MealDatabase):ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()

    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()

    private var categoriesLiveData = MutableLiveData<List<Category>>()

    private var favoriteMealsLiveData = mealDatabase.mealDao().getAllMeals()

    private var searchedMealsLiveData = MutableLiveData<List<Meal>>()

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("TAG", "onFailure: " )
            }

        });
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object :Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if (response.body() != null) {
                    val categoryMealsList: List<MealsByCategory> = response.body()!!.meals.map { meal ->
                        MealsByCategory().apply {
                            strMeal = meal.strMeal
                            strMealThumb = meal.strMealThumb
                            idMeal = meal.idMeal
                        }
                    }
                    popularItemsLiveData.value = categoryMealsList
                }
            }

            override fun onFailure(p0: Call<MealsByCategoryList>, p1: Throwable) {
                Log.d("TAG", "HomeFragment: ", )
            }

        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let {categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(p0: Call<CategoryList>, p1: Throwable) {
                Log.e("TAG", "HomeViewModel: ${p1.message.toString()}")
            }
        })
    }

    fun searchMeals(searchQuery:String) = RetrofitInstance.api.searchMeals(searchQuery).enqueue(
        object : Callback<MealList>{
        override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
            val mealsList = response.body()?.meals
            mealsList?.let{
                searchedMealsLiveData.postValue(it)
            }
        }

        override fun onFailure(p0: Call<MealList>, p1: Throwable) {
            Log.e("TAG", "HomeViewModel: ${p1.message.toString()}")
        }

    })

    fun observeSearchedMealsLiveData(): LiveData<List<Meal>> = searchedMealsLiveData


    fun observeRandomMealLivedata():LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularItemsLiveData():LiveData<List<MealsByCategory>>{
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData():LiveData<List<Category>>{
        return categoriesLiveData
    }

    fun observeFavoritesMealsLiveData():LiveData<List<Meal>>{
        return favoriteMealsLiveData
    }
}