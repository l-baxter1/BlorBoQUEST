package com.zybooks.blorboquest

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {

    private lateinit var moneyButton: ImageButton
    private lateinit var cashBox: TextView
    private lateinit var background: ConstraintLayout
    private lateinit var multiplierBox: TextView
    private lateinit var downgradeButton: ImageButton
    private lateinit var upgradeButton: ImageButton
    private lateinit var downgradeCostBox: TextView
    private lateinit var upgradeCostBox: TextView

    private var totalCash = 0.0
    private var cashPerClick = 1.0
    private var clickMultiplier = 1.0
    private var blorboMultiplier = 1000000.0
    private var downgradeCost = 1.0
    private var upgradeCost = 1.0
    private var abbr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setIcon(R.drawable.menu_icon)
        setSupportActionBar(findViewById(R.id.nav_menu))
        moneyButton = findViewById(R.id.moneyButton)
        cashBox = findViewById(R.id.cashBox)
        background = findViewById(R.id.main)
        upgradeCostBox = findViewById(R.id.upgradeCostBox)
        downgradeCostBox = findViewById(R.id.downgradeCostBox)
        upgradeButton = findViewById(R.id.upgradeButton)
        downgradeButton = findViewById(R.id.downgradeButton)
        multiplierBox = findViewById(R.id.multiplierBox)

        background.background = getDrawable(R.drawable.placeholder_bg)

        setMoneyBox(cashBox, totalCash, abbr)
        setMoneyBox(upgradeCostBox, upgradeCost, abbr)
        setMoneyBox(downgradeCostBox, downgradeCost, abbr)
        setMultBox(multiplierBox, clickMultiplier)

    }
//    val mainHandler = Handler(Looper.getMainLooper())
//    mainHandler.post(object : Runnable {
//        override fun run() {
//            mainHandler.postDelayed(this, 1000)
//        }
//    })
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_button ->{
                startActivity(Intent(this, MenuPage::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun onMoneyButtonClick(view: View) {
        totalCash += (cashPerClick * clickMultiplier)
        setMoneyBox(cashBox, totalCash, abbr)
        if (totalCash >= upgradeCost) {
            upgradeCostBox.setTextColor(Color.parseColor("#ffffff"))
        } else {
            upgradeCostBox.setTextColor(Color.parseColor("#ff0000"))
        }
        if (totalCash >= downgradeCost) {
            downgradeCostBox.setTextColor(Color.parseColor("#ffffff"))
        } else {
            downgradeCostBox.setTextColor(Color.parseColor("#ff0000"))
        }
    }
    fun onUpgradeButtonClick(view: View) {
        if (totalCash >= upgradeCost) {
            totalCash -= upgradeCost
            clickMultiplier *= 1.25
            upgradeCost *= 4
            setMultBox(multiplierBox, clickMultiplier)
            setMoneyBox(upgradeCostBox, upgradeCost, abbr)
            setMoneyBox(cashBox, totalCash, abbr)

            //update text colors
            if (totalCash < upgradeCost) {
                upgradeCostBox.setTextColor(Color.parseColor("#ff0000"))
            }
            if (totalCash < downgradeCost) {
                downgradeCostBox.setTextColor(Color.parseColor("#ff0000"))
            }
        }
    }
    fun onDowngradeButtonClick(view: View) {
        if (totalCash >= upgradeCost) {
            totalCash -= upgradeCost
            blorboMultiplier /= 1.25
            downgradeCost *= 4
            setMoneyBox(downgradeCostBox, downgradeCost, abbr)
            setMoneyBox(cashBox, totalCash, abbr)

            //update text colors
            if (totalCash < upgradeCost) {
                upgradeCostBox.setTextColor(Color.parseColor("#ff0000"))
            }
            if (totalCash < downgradeCost) {
                downgradeCostBox.setTextColor(Color.parseColor("#ff0000"))
            }
        }
    }
    fun setMoneyBox(box: TextView, value: Double, abbr: String) {
        box.text = "$" + value + abbr
    }
    fun setMultBox(box: TextView, value: Double) {
        box.text = "Multiplier: x" + value
    }
}