package com.androidexp.englishapp.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.androidexp.englishapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
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