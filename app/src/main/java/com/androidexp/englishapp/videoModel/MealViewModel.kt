package com.androidexp.englishapp.videoModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidexp.englishapp.db.MealDatabase
import com.androidexp.englishapp.model.Meal
import com.androidexp.englishapp.model.MealList
import com.androidexp.englishapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(val mealDatabase: MealDatabase):ViewModel() {
    private var mealDetailLiveData = MutableLiveData<Meal>()
    
    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    mealDetailLiveData.value = response.body()!!.meals[0]
                }
                else
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("TAG", "MealActivity")
            }
        })
    }

    fun observeMealDetailsLivedata():LiveData<Meal>{
        return mealDetailLiveData
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }


}