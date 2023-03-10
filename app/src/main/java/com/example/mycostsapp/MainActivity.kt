package com.example.mycostsapp

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mycostsapp.activities.HomepageActivity
import com.example.mycostsapp.activities.OfflineActivity
import com.example.mycostsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object{
        val GOOGLE_SHEET_DATA_EXTRA= "google_sheet_data_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getValuesFromGoogleSheet()
        //ObjectAnimator.ofInt(binding.pbLoading,"progress", binding.pbLoading.max).setDuration(2000)
    }

    private fun getValuesFromGoogleSheet(){

        val monthlyExpensesList = ArrayList<MonthlyExpenses>()

        ObjectAnimator.ofInt(binding.pbLoading,"progress", binding.pbLoading.max).setDuration(2000).start()

        val queue = Volley.newRequestQueue(this)
        val url = "https://script.google.com/macros/s/AKfycbwc0WvIcs_wzm53NKVZWH5Zys2-JwA6JOdZB7FlFm-J5I2UcugJcUi_UY9XY3lBbCXANA/exec"
        val jsonObjectRequest = object: JsonObjectRequest(
            Method.GET, url, null, Response.Listener {
                val data = it.getJSONArray("expenseValues")
                for(i in 0 until data.length()){
                    val monthlyExpenseJSONObject = data.getJSONObject(i)
                    val monthlyExpensesObject = MonthlyExpenses(
                        monthlyExpenseJSONObject.getString("name"),
                        monthlyExpenseJSONObject.getDouble("groceries"),
                        monthlyExpenseJSONObject.getDouble("car"),
                        monthlyExpenseJSONObject.getDouble("other"),
                        monthlyExpenseJSONObject.getDouble("bills"),
                        monthlyExpenseJSONObject.getDouble("totalExpenses"),
                        monthlyExpenseJSONObject.getDouble("totalSavings")
                    )
                    monthlyExpensesList.add(monthlyExpensesObject)
                }
                val intent = Intent(this, HomepageActivity::class.java)
                intent.putExtra(GOOGLE_SHEET_DATA_EXTRA, monthlyExpensesList)
                startActivity(intent)
                finish()
            },
            Response.ErrorListener {
                finish()
                val intent = Intent(this, OfflineActivity::class.java)
                startActivity(intent)
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()
            }
        }
        queue.add(jsonObjectRequest)
    }
}