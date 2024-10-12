package com.androidexp.englishapp.retrofit


import com.androidexp.englishapp.model.Meal
import com.androidexp.englishapp.model.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>
}