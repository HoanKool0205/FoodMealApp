package com.androidexp.englishapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androidexp.englishapp.R
import com.androidexp.englishapp.activity.MealActivity
import com.androidexp.englishapp.databinding.FragmentHomeBinding
import com.androidexp.englishapp.model.MealList
import com.androidexp.englishapp.model.Meal
import com.androidexp.englishapp.retrofit.RetrofitInstance
import com.androidexp.englishapp.videoModel.HomeViewModel
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel
    private var randomMeal: Meal? =null

    companion object{
        const val MEAL_ID = "com.androidexp.englishapp.fragment.idMeal"
        const val MEAL_NAME = "com.androidexp.englishapp.fragment.nameMeal"
        const val MEAL_THUMB = "com.androidexp.englishapp.fragment.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
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

        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()
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
        homeMvvm.observeRandomMealLivedata().observe(viewLifecycleOwner, Observer { meal ->
            Glide.with(this@HomeFragment)
                .load(meal.strMealThumb)
                .into(binding.imgRandomMeal)

            randomMeal = meal
        })
    }
}