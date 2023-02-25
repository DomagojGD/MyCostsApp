package com.example.mycostsapp.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mycostsapp.R
import com.example.mycostsapp.databinding.ActivityHomepageBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

private lateinit var binding: ActivityHomepageBinding

private lateinit var barChart: BarChart
private lateinit var barData: BarData
private lateinit var barDataSet: BarDataSet
private lateinit var barEntriesList: ArrayList<BarEntry>

class HomepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHomepage)

        if(supportActionBar != null){
            supportActionBar?.title = "Home"
        }

        barChart = binding.bcLastThreeMonthsBarChart

        barEntriesList = ArrayList()

        // on below line we are adding data
        // to our bar entries list
        barEntriesList.add(BarEntry(1f, 1f))
        barEntriesList.add(BarEntry(2f, 2f))
        barEntriesList.add(BarEntry(3f, 3f))
        barEntriesList.add(BarEntry(4f, 4f))
        barEntriesList.add(BarEntry(5f, 5f))

        barDataSet = BarDataSet(barEntriesList, "Bar Chart Data")
        barData = BarData(barDataSet)
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.setColor(R.color.purple_200)
        barDataSet.valueTextSize = 16f
        barChart.description.isEnabled = false

        //TODO Vidi hoćeš li uopće stavljati bar chart. Ako nećeš, izbriši dependency i repository
    }
}