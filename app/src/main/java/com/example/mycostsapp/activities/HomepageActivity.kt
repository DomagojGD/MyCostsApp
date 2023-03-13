package com.example.mycostsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mycostsapp.MainActivity
import com.example.mycostsapp.MonthlyExpenses
import com.example.mycostsapp.R
import com.example.mycostsapp.databinding.ActivityHomepageBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.math.RoundingMode

class HomepageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomepageBinding

    //Bar chart that shows information about spent money in last three months
    private lateinit var barChartSpent: BarChart
    //Data list which will be shown in the bar chart
    private lateinit var barEntriesListSpent: ArrayList<BarEntry>
    //Bar data set( bar data to be shown in the bar chart
    private lateinit var barDataSetSpent: BarDataSet
    private lateinit var barDataSpent: BarData

    //Bar chart that shows information about saved money in last three months
    private lateinit var barChartSaved: BarChart
    //Data list which will be shown in the bar chart
    private lateinit var barEntriesListSaved: ArrayList<BarEntry>
    //Bar data set( bar data to be shown in the bar chart
    private lateinit var barDataSetSaved: BarDataSet
    private lateinit var barDataSaved: BarData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHomepage)

        if(supportActionBar != null){
            supportActionBar?.title = "Home"
        }

        /*If there is extra information from previous main activity, get that information and set homepage activity*/
        if(intent.hasExtra(MainActivity.GOOGLE_SHEET_DATA_EXTRA)){

            //Get data from intent extra and store it in val monthlyExpensesList
            val monthlyExpensesList = intent.getSerializableExtra(MainActivity.GOOGLE_SHEET_DATA_EXTRA) as ArrayList<MonthlyExpenses>

            setUpLastMonthExpenses(monthlyExpensesList)
            setLastThreeMonthsExpenses(monthlyExpensesList)
            setLastThreeMonthsSavings(monthlyExpensesList)

            //Remove "please wait progress bar"
            binding.llPleaseWaitProgressBarHomepage.visibility = View.GONE

        }else{
            //this is for situation when homepage activity is not started from main activity
            getValuesFromGoogleSheet()
        }

        //Start AddNewCostActivity when button AddNewExpense is clicked
        binding.btnAddNewExpense.setOnClickListener {
            finish()
            val intent = Intent(this, AddNewCostActivity::class.java)
            startActivity(intent)
        }
    }

    // Set bar chart for spent money in last three months
    private fun setSpentBarChart(monthThreeValue: Float, monthTwoValue: Float, monthOneValue: Float){

        // Connect barChart variable to bar chart in xml layout
        barChartSpent = binding.bcSpentLastThreeMonthsBarChart

        // Set data list which will be shown in the bar chart
        barEntriesListSpent = ArrayList()

        // on below line we are adding data
        // to our bar entries list
        barEntriesListSpent.add(BarEntry(1f, monthThreeValue))
        barEntriesListSpent.add(BarEntry(2f, monthTwoValue))
        barEntriesListSpent.add(BarEntry(3f, monthOneValue))

        //Set colors list for bar chart data
        val colorsArray = IntArray(3)

        colorsArray[0] = R.color.base_green
        colorsArray[1] = R.color.app_yellow
        colorsArray[2] = R.color.app_blue

        // Initializing bar data set
        barDataSetSpent = BarDataSet(barEntriesListSpent, "")

        //Initializing bar data
        barDataSpent = BarData(barDataSetSpent)

        // Set colors of data bars
        barDataSetSpent.setColors(colorsArray, this)

        // Remove text from bars
        barDataSetSpent.valueTextSize = 0f

        // Setting data to the bar chart
        barChartSpent.data = barDataSpent

        // Remove text from x axis
        barChartSpent.xAxis.textColor = 0

        // Remove text from left and right y axis
        barChartSpent.axisLeft.textColor = 0
        barChartSpent.axisRight.textColor = 0

        // Disable bar chart description text
        barChartSpent.description.isEnabled = false

        // Disable any event if bar chart is touched
        barChartSpent.setTouchEnabled(false)

    }

    // Set bar chart for saved money in last three months
    private fun setSavedBarChart(monthThreeValue: Float, monthTwoValue: Float, monthOneValue: Float){

        // Connect barChart variable to bar chart in xml layout
        barChartSaved = binding.bcSavedLastThreeMonthsBarChart

        // Set data list which will be shown in the bar chart
        barEntriesListSaved = ArrayList()

        // on below line we are adding data
        // to our bar entries list
        barEntriesListSaved.add(BarEntry(1f, monthThreeValue))
        barEntriesListSaved.add(BarEntry(2f, monthTwoValue))
        barEntriesListSaved.add(BarEntry(3f, monthOneValue))

        //Set colors list for bar chart data
        val colorsArray = IntArray(3)

        colorsArray[0] = R.color.base_green
        colorsArray[1] = R.color.app_yellow
        colorsArray[2] = R.color.app_blue

        // Initializing bar data set
        barDataSetSaved = BarDataSet(barEntriesListSaved, "")

        //Initializing bar data
        barDataSaved = BarData(barDataSetSaved)

        // Set colors of data bars
        barDataSetSaved.setColors(colorsArray, this)

        // Remove text from bars
        barDataSetSaved.valueTextSize = 0f

        // Setting data to the bar chart
        barChartSaved.data = barDataSaved

        // Remove text from x axis
        barChartSaved.xAxis.textColor = 0

        // Remove text from left and right y axis
        barChartSaved.axisLeft.textColor = 0
        barChartSaved.axisRight.textColor = 0

        // Disable bar chart description text
        barChartSaved.description.isEnabled = false

        // Disable any event if bar chart is touched
        barChartSaved.setTouchEnabled(false)
    }

    //Function to get the data from google sheets with volley library
    private fun getValuesFromGoogleSheet(){

        //List which will contain information about last three months
        val monthlyExpensesList = ArrayList<MonthlyExpenses>()

        //Create new request queue to get data from google sheets
        val queue = Volley.newRequestQueue(this)

        //URL of google sheets app script that we are getting the data from
        val url = "https://script.google.com/macros/s/AKfycbxIvypoFYX55_x46aL3Sm0RM4PP4rL_ilDTY8sGNgnX5yqUggDDrBjDgqbCsYATyaX42Q/exec"

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
                //Set up homepage UI and "remove please wait" progress bar
                setUpLastMonthExpenses(monthlyExpensesList)
                setLastThreeMonthsExpenses(monthlyExpensesList)
                setLastThreeMonthsSavings(monthlyExpensesList)
                binding.llPleaseWaitProgressBarHomepage.visibility = View.GONE
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

    //Set up first cardview in homepage UI
    private fun setUpLastMonthExpenses(monthlyExpensesList: ArrayList<MonthlyExpenses>){

        /*Below is the data we get from google sheets: how much money is spent in these four categories,
        as well as info about total spendings and total savings. The data is for the current month.
        Note that this function is called in getValuesFromGoogleSheet() at JSON object request response listener*/
        val name = monthlyExpensesList[0].monthName
        val groceries = monthlyExpensesList[0].groceries.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val car = monthlyExpensesList[0].car.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val bills = monthlyExpensesList[0].bills.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val other = monthlyExpensesList[0].other.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val totalExpenses = monthlyExpensesList[0].totalExpenses.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val totalSavings = monthlyExpensesList[0].totalSavings.toBigDecimal().setScale(2, RoundingMode.HALF_UP)

        /**
         * Set all text views with information gotten from google sheets
         */
        binding.tvCurrentMonthAndYear.text = name
        binding.tvCurrentMonthGroceriesValue.text = groceries.toString()
        binding.tvCurrentMonthCarValue.text = car.toString()
        binding.tvCurrentMonthBillsValue.text = bills.toString()
        binding.tvCurrentMonthOtherValue.text = other.toString()
        binding.tvCurrentMonthTotalSpentValue.text = totalExpenses.toString()
        binding.tvCurrentMonthSavingsValue.text = totalSavings.toString()

        /**
         * If saved money is negative, text will be red
         */
        if(binding.tvCurrentMonthSavingsValue.text.toString().toDouble() < 0){
            binding.tvCurrentMonthSavingsValue.setTextColor(ContextCompat.getColor(this, R.color.red))
        }
    }

    //Set up second cardview in homepage UI
    private fun setLastThreeMonthsExpenses(monthlyExpensesList: ArrayList<MonthlyExpenses>){

        /**
         * Get the data on total spendings for last three months from google sheets.
         * Current month will be first in monthlyExpensesList, month before that will be second, etc.
         * Note that this function is called in getValuesFromGoogleSheet() at JSON object request response listener
         */
        val currentMonth = monthlyExpensesList[0].totalExpenses.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val lastMonth = monthlyExpensesList[1].totalExpenses.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val monthBeforeTheLast = monthlyExpensesList[2].totalExpenses.toBigDecimal().setScale(2, RoundingMode.HALF_UP)

        /**
         * Get names of last three months from google sheets
         */
        val currentMonthName = monthlyExpensesList[0].monthName
        val lastMonthName = monthlyExpensesList[1].monthName
        val monthBeforeTheLastName = monthlyExpensesList[2].monthName

        /**
         * Set text views for last three months names
         */
        binding.tvSpentMonthThree.text = currentMonthName + ":"
        binding.tvSpentMonthTwo.text = lastMonthName + ":"
        binding.tvSpentMonthOne.text = monthBeforeTheLastName + ":"

        /**
         * Set text views for total spendings in the last three months
         */
        binding.tvSpentMonthThreeValue.text = currentMonth.toString()
        binding.tvSpentMonthTwoValue.text = lastMonth.toString()
        binding.tvSpentMonthOneValue.text = monthBeforeTheLast.toString()

        /** Set bar chart for last three months total spendings*/
        setSpentBarChart(currentMonth.toFloat(), lastMonth.toFloat(), monthBeforeTheLast.toFloat())
        binding.bcSpentLastThreeMonthsBarChart.visibility = View.VISIBLE
    }

    //Set up third cardview in homepage UI
    private fun setLastThreeMonthsSavings(monthlyExpensesList: ArrayList<MonthlyExpenses>){

        /**
         * Get the data on total savings for last three months from google sheets.
         * Current month will be first in monthlyExpensesList, month before that will be second, etc.
         * Note that this function is called in getValuesFromGoogleSheet() at JSON object request response listener
         */
        val currentMonth = monthlyExpensesList[0].totalSavings.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val lastMonth = monthlyExpensesList[1].totalSavings.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val monthBeforeTheLast = monthlyExpensesList[2].totalSavings.toBigDecimal().setScale(2, RoundingMode.HALF_UP)

        /**
         * Get names of last three months from google sheets
         */
        val currentMonthName = monthlyExpensesList[0].monthName
        val lastMonthName = monthlyExpensesList[1].monthName
        val monthBeforeTheLastName = monthlyExpensesList[2].monthName

        /**
         * Set text views for last three months names
         */
        binding.tvSavedMonthThree.text = currentMonthName + ":"
        binding.tvSavedMonthTwo.text = lastMonthName + ":"
        binding.tvSavedMonthOne.text = monthBeforeTheLastName + ":"

        /** Set bar chart for last three months total savings*/
        binding.tvSavedMonthThreeValue.text = currentMonth.toString()
        binding.tvSavedMonthTwoValue.text = lastMonth.toString()
        binding.tvSavedMonthOneValue.text = monthBeforeTheLast.toString()

        /** Set bar chart for last three months total savings*/
        setSavedBarChart(currentMonth.toFloat(), lastMonth.toFloat(), monthBeforeTheLast.toFloat())
        binding.bcSavedLastThreeMonthsBarChart.visibility = View.VISIBLE
    }
}