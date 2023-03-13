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

    //Companion object for extra that will be transferred to homepage activity
    companion object{
        val GOOGLE_SHEET_DATA_EXTRA= "google_sheet_data_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getValuesFromGoogleSheet()
    }

    //Function to get the data from google sheets with volley library
    private fun getValuesFromGoogleSheet(){

        //List which will contain information about last three months
        val monthlyExpensesList = ArrayList<MonthlyExpenses>()

        //Start loading progress bar that will last for 2 seconds
        ObjectAnimator.ofInt(binding.pbLoading,"progress", binding.pbLoading.max).setDuration(2000).start()

        //Create new request queue to get data from google sheets
        val queue = Volley.newRequestQueue(this)

        //URL of google sheets app script that we are getting the data from
        val url = "https://script.google.com/macros/s/AKfycbwc0WvIcs_wzm53NKVZWH5Zys2-JwA6JOdZB7FlFm-J5I2UcugJcUi_UY9XY3lBbCXANA/exec"

        //JSON object request to get JSON from google sheets app script
        val jsonObjectRequest = object: JsonObjectRequest(
            Method.GET, url, null, Response.Listener {

                /*Data that we get from google sheets app script. It is JSON array named "expensesValues", named so in app script
                 *(line 72- "records.expenseValues = data;")*/
                val data = it.getJSONArray("expenseValues")

                /*For every JSON object in JSON array "data", get monthly expense information and put it in monthly expenses list*/
                for(i in 0 until data.length()){

                    //JSON object from JSON array "data"
                    val monthlyExpenseJSONObject = data.getJSONObject(i)

                    //Monthly expenses object with data gotten from "monthlyExpenseJSONObject"
                    val monthlyExpensesObject = MonthlyExpenses(
                        monthlyExpenseJSONObject.getString("name"),
                        monthlyExpenseJSONObject.getDouble("groceries"),
                        monthlyExpenseJSONObject.getDouble("car"),
                        monthlyExpenseJSONObject.getDouble("other"),
                        monthlyExpenseJSONObject.getDouble("bills"),
                        monthlyExpenseJSONObject.getDouble("totalExpenses"),
                        monthlyExpenseJSONObject.getDouble("totalSavings")
                    )
                    //Put monthly expenses object in "monthlyExpensesList"
                    monthlyExpensesList.add(monthlyExpensesObject)
                }

                //Start homepage activity with extra details- monthlyExpensesList
                val intent = Intent(this, HomepageActivity::class.java)
                intent.putExtra(GOOGLE_SHEET_DATA_EXTRA, monthlyExpensesList)
                startActivity(intent)
                finish()
            },
            Response.ErrorListener {

                //If there is no internet connection, start offline activity
                finish()
                val intent = Intent(this, OfflineActivity::class.java)
                startActivity(intent)
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()
            }
        }
        //Add jsonObjectRequest to request queue
        queue.add(jsonObjectRequest)
    }
}