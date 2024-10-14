package com.androidexp.englishapp.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.androidexp.englishapp.R
import com.androidexp.englishapp.databinding.ActivityMealBinding
import com.androidexp.englishapp.databinding.FragmentHomeBinding
import com.androidexp.englishapp.db.MealDatabase
import com.androidexp.englishapp.fragment.HomeFragment
import com.androidexp.englishapp.model.Meal
import com.androidexp.englishapp.videoModel.HomeViewModel
import com.androidexp.englishapp.videoModel.MealViewModel
import com.androidexp.englishapp.videoModel.MealViewModelFactory
import com.bumptech.glide.Glide

class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding: ActivityMealBinding
    private lateinit var youtubelink:String
    private lateinit var mealMvvm:MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        getMealInformationFromIntent()

        setInformationInViews()

        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()

        onYoutubeImageClick()

        onFavouriteClick()
    }

    private fun onFavouriteClick() {
        binding.btnAddFavorite.setOnClickListener{
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener(){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubelink))
            startActivity(intent)
        }
    }

    private var mealToSave:Meal?=null

    private fun observerMealDetailsLiveData() {
        mealMvvm.observeMealDetailsLivedata().observe(this,object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                onResponseCase()
                val meal = value
                mealToSave = meal

                binding.tvCategory.text = "Category: ${meal!!.strMeal}"
                binding.tvArea.text = "Area: ${meal!!.strArea}"
                binding.tvInstructionsRv.text = meal.strInstructions

                youtubelink = meal.strYoutube
            }
        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddFavorite.visibility = View.INVISIBLE
        binding.tvInstructionsRv.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddFavorite.visibility = View.VISIBLE
        binding.tvInstructionsRv.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}