package com.zybooks.blorboquest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var moneyButton: Button
    private lateinit var cashTextBox: TextView

    private var totalCash = 0
    private var cashPerClick = 1
    private var clickMultiplier = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moneyButton = findViewById(R.id.moneyButton)
        cashTextBox = findViewById(R.id.cashNumber)

        cashTextBox.text = totalCash.toString()
    }

    fun onMoneyButtonClick(view: View) {
        totalCash += (cashPerClick * clickMultiplier)
        cashTextBox.text = totalCash.toString()
    }

    fun onUpgradeButtonClick(view: View) {
        clickMultiplier *= 2
    }
}