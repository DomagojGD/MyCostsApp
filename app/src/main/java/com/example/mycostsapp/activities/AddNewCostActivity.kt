package com.example.mycostsapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mycostsapp.R
import com.example.mycostsapp.databinding.ActivityAddNewCostBinding

private lateinit var binding: ActivityAddNewCostBinding

class AddNewCostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewCostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAddNewCost)

        if(supportActionBar != null){
            supportActionBar?.title = "Add New Cost"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarAddNewCost.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnGroceries.setOnClickListener {
            it.setBackgroundResource(R.drawable.light_green_background)
            binding.btnCar.setBackgroundResource(R.drawable.btn_white_background)
            binding.btnBills.setBackgroundResource(R.drawable.btn_white_background)
            binding.btnOther.setBackgroundResource(R.drawable.btn_white_background)

            binding.btnGroceries.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.btnCar.setTextColor(ContextCompat.getColor(this, R.color.base_green))
            binding.btnBills.setTextColor(ContextCompat.getColor(this, R.color.base_green))
            binding.btnOther.setTextColor(ContextCompat.getColor(this, R.color.base_green))
        }

        binding.btnCar.setOnClickListener {
            it.setBackgroundResource(R.drawable.light_green_background)
            binding.btnGroceries.setBackgroundResource(R.drawable.btn_white_background)
            binding.btnBills.setBackgroundResource(R.drawable.btn_white_background)
            binding.btnOther.setBackgroundResource(R.drawable.btn_white_background)

            binding.btnGroceries.setTextColor(ContextCompat.getColor(this, R.color.base_green))
            binding.btnCar.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.btnBills.setTextColor(ContextCompat.getColor(this, R.color.base_green))
            binding.btnOther.setTextColor(ContextCompat.getColor(this, R.color.base_green))
        }

        binding.btnBills.setOnClickListener {
            it.setBackgroundResource(R.drawable.light_green_background)
            binding.btnCar.setBackgroundResource(R.drawable.btn_white_background)
            binding.btnGroceries.setBackgroundResource(R.drawable.btn_white_background)
            binding.btnOther.setBackgroundResource(R.drawable.btn_white_background)

            binding.btnGroceries.setTextColor(ContextCompat.getColor(this, R.color.base_green))
            binding.btnCar.setTextColor(ContextCompat.getColor(this, R.color.base_green))
            binding.btnBills.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.btnOther.setTextColor(ContextCompat.getColor(this, R.color.base_green))
        }

        binding.btnOther.setOnClickListener {
            it.setBackgroundResource(R.drawable.light_green_background)
            binding.btnCar.setBackgroundResource(R.drawable.btn_white_background)
            binding.btnBills.setBackgroundResource(R.drawable.btn_white_background)
            binding.btnGroceries.setBackgroundResource(R.drawable.btn_white_background)

            binding.btnGroceries.setTextColor(ContextCompat.getColor(this, R.color.base_green))
            binding.btnCar.setTextColor(ContextCompat.getColor(this, R.color.base_green))
            binding.btnBills.setTextColor(ContextCompat.getColor(this, R.color.base_green))
            binding.btnOther.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        //TODO Dodaj opise i date picker dialog
    }
}