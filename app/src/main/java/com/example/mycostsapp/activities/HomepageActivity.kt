package com.example.mycostsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mycostsapp.R
import com.example.mycostsapp.databinding.ActivityHomepageBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

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

        setSpentBarChart()
        setSavedBarChart()

        binding.btnAddNewExpense.setOnClickListener {
            val intent = Intent(this, AddNewCostActivity::class.java)
            startActivity(intent)
        }
    }

    // Set bar chart for spent money in last three months
    private fun setSpentBarChart(){

        // Connect barChart variable to bar chart in xml layout
        barChartSpent = binding.bcSpentLastThreeMonthsBarChart

        // Set data list which will be shown in the bar chart
        barEntriesListSpent = ArrayList()

        // Get three values to be shown in the bar chart
        val monthThreeValue = binding.tvSpentMonthThreeValue.text.toString().toFloat()
        val monthTwoValue = binding.tvSpentMonthTwoValue.text.toString().toFloat()
        val monthOneValue = binding.tvSpentMonthOneValue.text.toString().toFloat()

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
    private fun setSavedBarChart(){

        // Connect barChart variable to bar chart in xml layout
        barChartSaved = binding.bcSavedLastThreeMonthsBarChart

        // Set data list which will be shown in the bar chart
        barEntriesListSaved = ArrayList()

        // Get three values to be shown in the bar chart
        val monthThreeValue = binding.tvSavedMonthThreeValue.text.toString().toFloat()
        val monthTwoValue = binding.tvSavedMonthTwoValue.text.toString().toFloat()
        val monthOneValue = binding.tvSavedMonthOneValue.text.toString().toFloat()

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
}