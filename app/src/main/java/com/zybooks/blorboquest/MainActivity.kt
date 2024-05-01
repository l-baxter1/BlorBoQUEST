package com.zybooks.blorboquest

import android.content.Intent
import android.os.Bundle
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
    private lateinit var downgradeButton: Button
    private lateinit var upgradeButton: Button
    private lateinit var downgradeCostBox: TextView
    private lateinit var upgradeCostBox: TextView

    private var totalCash = 0
    private var cashPerClick = 1
    private var clickMultiplier = 1
    private var blorboMultiplier = 1
    private var downgradeCost = 1
    private var upgradeCost = 1
    private var abbr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setIcon(R.drawable.menu_icon)
        setSupportActionBar(findViewById(R.id.nav_menu))
        moneyButton = findViewById(R.id.moneyButton)
        cashBox = findViewById(R.id.cashBox)
        background = findViewById(R.id.main)

        background.background = getDrawable(R.drawable.placeholder_bg)

        setMoneyBox(cashBox, totalCash, abbr)
        setMoneyBox(upgradeCostBox, upgradeCost, abbr)
        setMoneyBox(downgradeCostBox, downgradeCost, abbr)
        setMultBox(cashBox, totalCash)

    }
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
    }
    fun onUpgradeButtonClick(view: View) {
        if (totalCash >= upgradeCost) {
            totalCash -= upgradeCost
            clickMultiplier *= 2
            upgradeCost *= 4
            setMultBox(multiplierBox, clickMultiplier)
            setMoneyBox(upgradeCostBox, totalCash, abbr)
        }
    }
    fun setMoneyBox(box: TextView, value: Int, abbr: String) {
        box.text = "$" + value + abbr
    }
    fun setMultBox(box: TextView, value: Int) {
        //box.text = (value + "x").toString()
    }
}