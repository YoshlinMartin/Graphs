package com.example.graphs

import android.R.attr.label
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.ValueFormatter


class MainActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private lateinit var inputValue: EditText
    private lateinit var addButton: Button
    private lateinit var button: Button
    private lateinit var inputLabel: EditText

    private val barEntries = ArrayList<BarEntry>()


    private val labels = ArrayList<String>()
    private var xValue = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        barChart = findViewById(R.id.barChart)
        inputValue = findViewById(R.id.inputValue)
        addButton = findViewById(R.id.addButton)
        button = findViewById(R.id.button)
        inputLabel = findViewById(R.id.inputLabel)

        button.setOnClickListener{
            val intent = Intent(this, PieChart::class.java)
            startActivity(intent)

        }

        setupChart()

        addButton.setOnClickListener {

            val label = inputLabel.text.toString()
            val valueText = inputValue.text.toString()

            if (valueText.isNotEmpty()) {

                val value = valueText.toFloat()

                // Add new bar
                barEntries.add(BarEntry(xValue, value))
                labels.add(label)

                xValue++

                updateChart()

                inputValue.text.clear()
                inputLabel.text.clear()
            }
        }

    }


    private fun setupChart() {

        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        //places the label at the initial entry
        xAxis.granularity = 0f
        xAxis.setDrawGridLines(false)

        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return if (index >= 0 && index < labels.size) {
                    labels[index]
                } else {
                    ""
                }
            }
        }


    }

    private fun updateChart() {

        val dataSet = BarDataSet(barEntries, "My Data")

        // 🎨 different color per bar
        val colors = ArrayList<Int>()

        for (i in barEntries.indices) {
            colors.add(
                Color.rgb(
                    (50..255).random(),
                    (50..255).random(),
                    (50..255).random()
                )
            )
        }

        dataSet.colors = colors

        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 16f

        val data = BarData(dataSet)

        barChart.data = data

        barChart.animateXY(1000, 1000)

        barChart.invalidate()

    }


}