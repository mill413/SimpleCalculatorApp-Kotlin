package com.example.app1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.example.app1.R
import kotlinx.android.synthetic.main.activity_bmi.*

class BMIActivity : AppCompatActivity() {
    var height = 0.0
    var weight = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        btn_calbmi.setOnClickListener {
            bmi_res.text = getBMI()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    private fun getBMI():String{

        var height = text_height.text.toString().toDouble()
        val weight = text_weight.text.toString().toDouble()

        height /= 100
//        println("height=${height},weight=${weight}")
        return if(height == 0.0) "NAN"
        else String.format("%.2f",(weight/height*height))
            //(weight*weight/height).toString()
    }


}