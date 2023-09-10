package com.example.relevar.ManagementModule.StastisticsManagement

import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.relevar.ManagementModule.StorageManagement.BDData
import com.example.relevar.R
import com.example.relevar.databinding.ActivityStatsBinding
import org.json.JSONException
import org.json.JSONObject

class Stats : AppCompatActivity() {

    private lateinit var binding: ActivityStatsBinding
    private lateinit var adapter: HorizontalBarChartAdapter

    private var list = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16)
    private var list_hombres_values = arrayListOf<Int>()
    private var list_mujeres_values = arrayListOf<Int>()
    private var list_categorias_edades = arrayListOf<String>()
    private var list_mujeres = arrayListOf<Int>(2,1,6,10,9,25,5,5,8,9,1,2,7,22,5,7)
    private var list_edades = arrayListOf<String>("0-1", "2-5", "6-9", "10-14", "15-19", "20-24", "25-29", "30-34", "35-39", "40-44", "45-49", "50-54", "55-59", "60-64", "65-59", "70 y +")
    private var maxValue = 0
    private var maxValueOnXAxis = 0
    private var granularity = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        // Eliminar el action bar
        val actionbar = supportActionBar!!
        actionbar.hide()

        binding = ActivityStatsBinding.inflate(layoutInflater);
        setContentView(binding.root)

        //Data piramide
        var piramide = JSONObject()
        val adminBDData = BDData(baseContext, "BDData", null, 1)
        piramide = try {
            adminBDData.PiramidePoblacional()
        } catch (e: JSONException) {
            throw RuntimeException(e)
        }

        try {
            val keys = piramide.getJSONObject("M").keys()
            while (keys.hasNext()) {
                val k = keys.next()
                list_categorias_edades.add(k)
                list_hombres_values.add(piramide.getJSONObject("M").getInt(k))
                list_mujeres_values.add(piramide.getJSONObject("F").getInt(k))
            }
        } catch (e: JSONException) {
            throw java.lang.RuntimeException(e)
        }

        getGranularity()
        addTextViewForXAxis(maxValue)
        setGraphRecyclerViewAdapter()
    }

    private fun setGraphRecyclerViewAdapter() {
        adapter = HorizontalBarChartAdapter(list_hombres_values, list_mujeres_values, list_categorias_edades, maxValueOnXAxis)
        binding.horizontalBarChartRecyclerview.adapter = adapter
    }

    private fun addTextViewForXAxis(maxValue: Int) {
        when {
            maxValue > 10 -> createTextView(5)
            maxValue > 8 -> createTextView(5)
            maxValue > 6 -> createTextView(4)
            maxValue > 4 -> createTextView(3)
            maxValue > 2 -> createTextView(2)
            else -> createTextView(1)

        }
    }

    private fun createTextView(numberOfTextView : Int) {
        generateOriginLabelForXAxis()
        for (index in 1..numberOfTextView){
            generateXAxisTextView((index * granularity).toString())
        }
    }

    private fun generateOriginLabelForXAxis() {
        val textView = TextView(this)
        textView.text = "0"
        binding.xAxisTextviewContainerLayout.addView(textView)
    }

    private fun generateXAxisTextView(text: String) {
        val txtView = TextView(this)
        txtView.text = text
        txtView.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
        txtView.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
                1f
        )
        binding.xAxisTextviewContainerLayout.addView(txtView)
    }

    private fun getGranularity() {
        maxValue = list_hombres_values.maxOrNull() ?: 0
        var maxMujeres = list_mujeres_values.maxOrNull() ?: 0
        if (maxMujeres > maxValue) { maxValue = list_mujeres_values.maxOrNull() ?: 0 }
        caculateGranularity()
    }

    private fun caculateGranularity() {
        when {
            maxValue > 10 -> {
                maxValueOnXAxis = maxValue + (5  - (maxValue % 5))
                granularity = maxValueOnXAxis / 5
            }
            maxValue > 8 -> {
                maxValueOnXAxis = 10
                granularity = 2
            }
            maxValue > 6 -> {
                maxValueOnXAxis = 8
                granularity = 2
            }
            maxValue > 4 -> {
                maxValueOnXAxis = 6
                granularity = 2
            }
            maxValue > 2 -> {
                maxValueOnXAxis = 4
                granularity = 2
            }
            maxValue > 1 -> {
                maxValueOnXAxis = 2
                granularity = 2
            }
            else -> {
                maxValueOnXAxis = 1
                granularity = 1
            }
        }
    }


}