package com.example.mycostsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.mycostsapp.MonthlyExpenses
import com.example.mycostsapp.R
import com.example.mycostsapp.databinding.ActivityHomepageBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.math.RoundingMode

private lateinit var binding: ActivityHomepageBinding

private lateinit var barChartSpent: BarChart
private lateinit var barEntriesListSpent: ArrayList<BarEntry>
private lateinit var barDataSetSpent: BarDataSet
private lateinit var barDataSpent: BarData

private lateinit var barChartSaved: BarChart
private lateinit var barEntriesListSaved: ArrayList<BarEntry>
private lateinit var barDataSetSaved: BarDataSet
private lateinit var barDataSaved: BarData

class HomepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHomepage)

        if(supportActionBar != null){
            supportActionBar?.title = "Home"
        }

        //setSpentBarChart()
        //setSavedBarChart()

        getValuesFromGoogleSheet()

        binding.btnAddNewExpense.setOnClickListener {
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

        // Get three values to be shown in the bar chart
        /*val monthThreeValue = binding.tvSpentMonthThreeValue.text.toString().toFloat()
        val monthTwoValue = binding.tvSpentMonthTwoValue.text.toString().toFloat()
        val monthOneValue = binding.tvSpentMonthOneValue.text.toString().toFloat()*/

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

        // Get three values to be shown in the bar chart
        /*val monthThreeValue = binding.tvSavedMonthThreeValue.text.toString().toFloat()
        val monthTwoValue = binding.tvSavedMonthTwoValue.text.toString().toFloat()
        val monthOneValue = binding.tvSavedMonthOneValue.text.toString().toFloat()*/

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

    private fun getValuesFromGoogleSheet(){

        val monthlyExpensesList = ArrayList<MonthlyExpenses>()

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
                setUpLastMonthExpenses(monthlyExpensesList)
                setLastThreeMonthsExpenses(monthlyExpensesList)
                setLastThreeMonthsSavings(monthlyExpensesList)
            },
            Response.ErrorListener {
                Toast.makeText(this@HomepageActivity, it.toString(), Toast.LENGTH_LONG).show()
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()
            }
        }
        queue.add(jsonObjectRequest)
    }

    private fun setUpLastMonthExpenses(monthlyExpensesList: ArrayList<MonthlyExpenses>){

        val name = monthlyExpensesList[0].monthName
        val groceries = monthlyExpensesList[0].groceries.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val car = monthlyExpensesList[0].car.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val bills = monthlyExpensesList[0].bills.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val other = monthlyExpensesList[0].other.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val totalExpenses = monthlyExpensesList[0].totalExpenses.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val totalSavings = monthlyExpensesList[0].totalSavings.toBigDecimal().setScale(2, RoundingMode.HALF_UP)

        binding.tvCurrentMonthAndYear.text = name
        binding.tvCurrentMonthGroceriesValue.text = groceries.toString()
        binding.tvCurrentMonthCarValue.text = car.toString()
        binding.tvCurrentMonthBillsValue.text = bills.toString()
        binding.tvCurrentMonthOtherValue.text = other.toString()
        binding.tvCurrentMonthTotalSpentValue.text = totalExpenses.toString()
        binding.tvCurrentMonthSavingsValue.text = totalSavings.toString()
    }

    private fun setLastThreeMonthsExpenses(monthlyExpensesList: ArrayList<MonthlyExpenses>){
        val currentMonth = monthlyExpensesList[0].totalExpenses.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val lastMonth = monthlyExpensesList[1].totalExpenses.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val monthBeforeTheLast = monthlyExpensesList[2].totalExpenses.toBigDecimal().setScale(2, RoundingMode.HALF_UP)

        val currentMonthName = monthlyExpensesList[0].monthName
        val lastMonthName = monthlyExpensesList[1].monthName
        val monthBeforeTheLastName = monthlyExpensesList[2].monthName

        binding.tvSpentMonthThree.text = currentMonthName + ":"
        binding.tvSpentMonthTwo.text = lastMonthName + ":"
        binding.tvSpentMonthOne.text = monthBeforeTheLastName + ":"

        binding.tvSpentMonthThreeValue.text = currentMonth.toString()
        binding.tvSpentMonthTwoValue.text = lastMonth.toString()
        binding.tvSpentMonthOneValue.text = monthBeforeTheLast.toString()

        setSpentBarChart(currentMonth.toFloat(), lastMonth.toFloat(), monthBeforeTheLast.toFloat())
    }

    private fun setLastThreeMonthsSavings(monthlyExpensesList: ArrayList<MonthlyExpenses>){
        val currentMonth = monthlyExpensesList[0].totalSavings.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val lastMonth = monthlyExpensesList[1].totalSavings.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
        val monthBeforeTheLast = monthlyExpensesList[2].totalSavings.toBigDecimal().setScale(2, RoundingMode.HALF_UP)

        val currentMonthName = monthlyExpensesList[0].monthName
        val lastMonthName = monthlyExpensesList[1].monthName
        val monthBeforeTheLastName = monthlyExpensesList[2].monthName

        binding.tvSavedMonthThree.text = currentMonthName + ":"
        binding.tvSavedMonthTwo.text = lastMonthName + ":"
        binding.tvSavedMonthOne.text = monthBeforeTheLastName + ":"

        binding.tvSavedMonthThreeValue.text = currentMonth.toString()
        binding.tvSavedMonthTwoValue.text = lastMonth.toString()
        binding.tvSavedMonthOneValue.text = monthBeforeTheLast.toString()

        setSavedBarChart(currentMonth.toFloat(), lastMonth.toFloat(), monthBeforeTheLast.toFloat())
    }

    //TODO nauči kako se stavlja splash screen i onda će bar chart biti odmah prikazan
    //TODO dodaj opis funkcija i varijabli
    //TODO Pogledaj šta se događa kad nema interneta i odluči što ćeš tada napraviti. Napravi offline activity.
    //TODO kad uđeš u AddNewCostActivity, homepage se ne reloada. Popravi.
}