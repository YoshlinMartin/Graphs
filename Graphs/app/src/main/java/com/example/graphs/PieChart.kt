package com.example.graphs

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// MPAndroidChart imports
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class PieChart : AppCompatActivity() {

    private lateinit var pieChart: PieChart
    private lateinit var labelInput: EditText
    private lateinit var valueInput: EditText
    private lateinit var addPieButton: Button

    // IMPORTANT: You forgot this
    private val pieEntries = ArrayList<PieEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_pie_chart)

        pieChart = findViewById(R.id.pieChart)
        labelInput = findViewById(R.id.labelInput)
        valueInput = findViewById(R.id.valueInput)
        addPieButton = findViewById(R.id.addPieButton)

        setupPieChart()

        addPieButton.setOnClickListener {

            val label = labelInput.text.toString()

            val valueText = valueInput.text.toString()

            if (label.isNotEmpty() && valueText.isNotEmpty()) {

                val value = valueText.toFloat()

                // Add data to chart
                pieEntries.add(PieEntry(value, label))

                // Refresh chart
                updatePieChart()

                // Clear inputs
                labelInput.text.clear()
                valueInput.text.clear()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pieChart)) { v, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }
    }

    private fun setupPieChart() {

        pieChart.description.isEnabled = false

        pieChart.centerText = "Sales"

        pieChart.setCenterTextSize(22f)

        pieChart.animateY(1000)
    }

    private fun updatePieChart() {

        val dataSet = PieDataSet(pieEntries, "Categories")

        dataSet.colors = listOf(
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.YELLOW,
            Color.MAGENTA,
            Color.CYAN
        )

        dataSet.valueTextSize = 16f

        dataSet.valueTextColor = Color.BLACK

        val data = PieData(dataSet)

        pieChart.data = data

        pieChart.invalidate()
        pieChart.animateXY(1500, 1500)
    }
}


