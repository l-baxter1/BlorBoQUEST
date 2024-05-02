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
import androidx.core.view.isVisible
import kotlinx.coroutines.delay


class MainActivity : AppCompatActivity() {

    private lateinit var moneyButton: ImageButton
    private lateinit var cashBox: TextView
    private lateinit var background: ConstraintLayout
    private lateinit var multiplierBox: TextView
    private lateinit var downgradeButton: ImageButton
    private lateinit var upgradeButton: ImageButton
    private lateinit var downgradeCostBox: TextView
    private lateinit var upgradeCostBox: TextView
    private lateinit var mainHandler: Handler
    private lateinit var flashText: TextView

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
        mainHandler = Handler(Looper.getMainLooper())
        flashText = findViewById(R.id.moneyStolenText)

        background.background = getDrawable(R.drawable.placeholder_bg)
        flashText.visibility = View.GONE

        setMoneyBox(cashBox, totalCash, abbr)
        setMoneyBox(upgradeCostBox, upgradeCost, abbr)
        setMoneyBox(downgradeCostBox, downgradeCost, abbr)
        setMultBox(multiplierBox, clickMultiplier)

        mainHandler.post(object: Runnable {
            override fun run() {
                //doesn't steal if the player has no money
                if (totalCash > 0.0) {
                    blorboStealsYourMoneyLoser()
                    makeTextFlash()
                }
                //checks for money every 5s
                mainHandler.postDelayed(this, 5000) //5 sec
            }
        })
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
    fun blorboStealsYourMoneyLoser() {
        totalCash -= blorboMultiplier
        if(totalCash < 0) {
            totalCash = 0.0
        }
        setMoneyBox(cashBox, totalCash, abbr)

        //update text colors
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
    fun makeTextFlash() {
        flashText.visibility = View.VISIBLE
        Handler().postDelayed({
            flashText.setTextColor(Color.parseColor("#ff0000"))
            Handler().postDelayed({
                flashText.setTextColor(Color.parseColor("#ffffff"))
                Handler().postDelayed({
                    flashText.visibility = View.GONE
                }, 500)
            }, 500)
        }, 500)
    }
    fun onMoneyButtonClick(view: View) {
        totalCash += (cashPerClick * clickMultiplier)

        //truncates numbers to two decimal points
        totalCash = Math.round(totalCash * 10.0) / 10.0

        setMoneyBox(cashBox, totalCash, abbr)

        //update text colors
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
            if (clickMultiplier <= 10) {
                clickMultiplier *= 2
            } else {
                clickMultiplier += upgradeCost / 25
            }
            upgradeCost *= 4

            //truncates numbers to two decimal points
            clickMultiplier = Math.round(clickMultiplier * 10.0) / 10.0
            totalCash = Math.round(totalCash * 10.0) / 10.0

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
        if (totalCash >= downgradeCost) {
            totalCash -= downgradeCost
            blorboMultiplier /= 5
            downgradeCost *= 4

            //truncates numbers to two decimal points
            blorboMultiplier = Math.round(blorboMultiplier * 10.0) / 10.0
            totalCash = Math.round(totalCash * 10.0) / 10.0

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
        //truncates numbers to two decimal points
        Math.round(value * 10.0) / 10.0

        box.text = "$" + value + abbr
    }
    fun setMultBox(box: TextView, value: Double) {
        box.text = "Multiplier: x" + value
    }
    fun findAbbr() {

    }
}