package com.example.mycostsapp.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mycostsapp.R
import com.example.mycostsapp.databinding.ActivityAddNewCostBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNewCostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewCostBinding

    //Set calendar variable
    private var calendar = Calendar.getInstance()
    //Set date listener variable
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener

    //a number to know which category was selected
    private var costCategoryNumber = 0

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

            //Category one selected
            costCategoryNumber = 1
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

            //Category two selected
            costCategoryNumber = 2
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

            //Category four selected
            costCategoryNumber = 4
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

            //Category three selected
            costCategoryNumber = 3
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

        binding.btnAddNewCostFinish.setOnClickListener {
            binding.llPleaseWaitProgressBarAddNewCost.visibility = View.VISIBLE
            sendDataToGoogleSheet()
        }
    }

    private fun sendDataToGoogleSheet(){

        if(costCategoryNumber == 0 || binding.etEnterCostDate.text!!.isEmpty() ||
            binding.etEnterCostDescription.text!!.isEmpty() || binding.etEnterCostAmount.text!!.isEmpty()){

            //All data has to be entered
            Toast.makeText(this@AddNewCostActivity, "Please enter all data!", Toast.LENGTH_LONG).show()
        }else{
            val url = "https://script.google.com/macros/s/AKfycbwc0WvIcs_wzm53NKVZWH5Zys2-JwA6JOdZB7FlFm-J5I2UcugJcUi_UY9XY3lBbCXANA/exec"
            val stringRequest = object: StringRequest(Method.POST, url,
                Response.Listener {
                    Toast.makeText(this@AddNewCostActivity, it.toString(), Toast.LENGTH_LONG).show()
                    binding.llPleaseWaitProgressBarAddNewCost.visibility = View.GONE
                    clearNewCostsValues()
                },
                Response.ErrorListener {
                    Toast.makeText(this@AddNewCostActivity, it.toString(), Toast.LENGTH_LONG).show()
                }){
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()

                    val costAmountText = binding.etEnterCostAmount.text.toString()
                    val costAmountWithComa = costAmountText.replace(".", ",")

                    params["category"] = costCategoryNumber.toString()
                    params["date"] = binding.etEnterCostDate.text.toString()
                    params["description"] = binding.etEnterCostDescription.text.toString()
                    //params["amount"] = binding.etEnterCostAmount.text.toString()
                    params["amount"] = costAmountWithComa

                    return params
                }
            }

            val queue = Volley.newRequestQueue(this@AddNewCostActivity)
            queue.add(stringRequest)
        }
    }

    private fun clearNewCostsValues(){
        binding.btnGroceries.setBackgroundResource(R.drawable.btn_white_background)
        binding.btnCar.setBackgroundResource(R.drawable.btn_white_background)
        binding.btnBills.setBackgroundResource(R.drawable.btn_white_background)
        binding.btnOther.setBackgroundResource(R.drawable.btn_white_background)

        binding.btnGroceries.setTextColor(ContextCompat.getColor(this, R.color.base_green))
        binding.btnCar.setTextColor(ContextCompat.getColor(this, R.color.base_green))
        binding.btnBills.setTextColor(ContextCompat.getColor(this, R.color.base_green))
        binding.btnOther.setTextColor(ContextCompat.getColor(this, R.color.base_green))

        binding.etEnterCostDate.text!!.clear()
        binding.etEnterCostDescription.text!!.clear()
        binding.etEnterCostAmount.text!!.clear()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
    }
}

//TODO dodaj opis i ovdje i u app scriptu