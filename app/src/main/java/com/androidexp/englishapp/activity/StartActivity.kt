package com.androidexp.englishapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androidexp.englishapp.R

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

 //       val btnNext: Button = findViewById(R.id.btnNext)
//        btnNext.setOnClickListener {
//            Log.d("TAG", "onCreate: click")
//            startActivity(Intent(this, ChooseLocationActivity::class.java))
//            finish()
//        }
    }
}