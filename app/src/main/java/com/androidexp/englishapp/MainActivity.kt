package com.androidexp.englishapp;

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.androidexp.englishapp.db.MealDatabase
import com.androidexp.englishapp.videoModel.HomeViewModel
import com.androidexp.englishapp.videoModel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val viewModel: HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.fragmentContainerView)
        val tvNavbar = findViewById<BottomNavigationView>(R.id.tvNavbar)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.d("Navigation", "Navigated to ${destination.label}")
        }

        tvNavbar.setupWithNavController(navController)
    }
}