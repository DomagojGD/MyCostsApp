package com.example.mycostsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mycostsapp.databinding.ActivityOfflineBinding

class OfflineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOfflineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOfflineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvOffline.text = "Please check your internet connection!"

        //If internet connection is restored, homepage activity will be shown.
        binding.btnTryAgain.setOnClickListener {
            finish()
            val intent = Intent(this, HomepageActivity::class.java)
            startActivity(intent)
        }
    }
}