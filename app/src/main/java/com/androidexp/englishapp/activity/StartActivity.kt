package com.androidexp.englishapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.androidexp.englishapp.R
import com.androidexp.englishapp.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private val binding:ActivityStartBinding by lazy {
        ActivityStartBinding.inflate(layoutInflater)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val btnNext: Button = findViewById(R.id.btnNext)
        btnNext.setOnClickListener {
            Log.d("TAG", "onCreate: click")
            startActivity(Intent(this,  LoginActivity::class.java))
            finish()
        }
    }
}