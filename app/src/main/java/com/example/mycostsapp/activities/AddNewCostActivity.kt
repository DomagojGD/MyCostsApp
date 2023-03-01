package com.example.mycostsapp.activities

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mycostsapp.R
import com.example.mycostsapp.databinding.ActivityAddNewCostBinding
import java.text.SimpleDateFormat
import java.util.*

private lateinit var binding: ActivityAddNewCostBinding

//Set calendar variable
private var calendar = Calendar.getInstance()
//Set date listener variable
private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener

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

        //Set back button on toolbar
        binding.toolbarAddNewCost.setNavigationOnClickListener {
            onBackPressed()
        }

        //When 'groceries' button is clicked, its color turns green and the rest white.
        // Also, button text turns white, and text of rest of the buttons turns green.
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

        //When 'car' button is clicked, its color turns green and the rest white.
        // Also, button text turns white, and text of rest of the buttons turns green.
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

        //When 'bills' button is clicked, its color turns green and the rest white.
        // Also, button text turns white, and text of rest of the buttons turns green.
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

        //When 'other' button is clicked, its color turns green and the rest white.
        // Also, button text turns white, and text of rest of the buttons turns green.
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

        //Show date picker dialog when 'etEnterCostDate' is clicked.
        binding.etEnterCostDate.setOnClickListener {

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            //Set date set listener: Create date picker dialog.
            // When the date is chosen, text of 'etEnterCostDate' will be changed accordingly.
            dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->

                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                binding.etEnterCostDate.setText(sdf.format(calendar.time).toString())
            }

            DatePickerDialog(this@AddNewCostActivity, dateSetListener, year, month, dayOfMonth).show()
        }
    }
}