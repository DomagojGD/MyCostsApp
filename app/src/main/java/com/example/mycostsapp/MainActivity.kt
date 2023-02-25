package com.example.mycostsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mycostsapp.activities.HomepageActivity
import com.example.mycostsapp.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.flGoogleIcon.setOnClickListener {
            val intent = Intent(this, HomepageActivity::class.java)
            startActivity(intent)
        }
    }
}