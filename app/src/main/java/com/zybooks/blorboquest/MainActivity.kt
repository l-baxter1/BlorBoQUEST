package com.zybooks.blorboquest

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.icu.text.CompactDecimalFormat
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kotlinx.coroutines.delay
import com.zybooks.blorboquest.UpgradeOptionsFragment
import androidx.fragment.app.Fragment
import android.view.KeyEvent
import java.util.Locale


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
    private lateinit var resetButton: Button

    private var totalCash = 0.0
    private var cashPerClick = 1.0
    private var clickMultiplier = 1.0
    private var blorboMultiplier = 1.0
    private var downgradeCost = 1.0
    private var upgradeCost = 1.0

    private var upgradeFragmentVisible = false

    private val upgradeOptions = listOf(
        UpgradeOption("Money Laundering Upgrade", 400, "Upgrades money multiplier by x30"),
        UpgradeOption("Weapon Upgrade", 5000, "Unlocks an ending but that's coded later"),
        UpgradeOption("Autoclicker Upgrade", 20, "Adds 1 automatic click per upgrade")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize save data file
        val saveDataFile = getSharedPreferences("saveFile", Context.MODE_PRIVATE)
        var saveDataEditor = saveDataFile.edit()

        setValuesFromFile()

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
        //resetButton = findViewById(R.id.reset_button)

        background.background = getDrawable(R.drawable.placeholder_bg)
        flashText.visibility = View.GONE

        setMoneyBox(cashBox, totalCash)
        setMoneyBox(upgradeCostBox, upgradeCost)
        setMoneyBox(downgradeCostBox, downgradeCost)
        setMultBox(multiplierBox, clickMultiplier)

        upgradeButton.setOnClickListener {
            showUpgradeOptions()
        }

        mainHandler.post(object: Runnable {
            override fun run() {
                var chance = maybeBlorboGetsMorePowerful()
                System.out.println(blorboMultiplier)
                //doesn't steal if the player has no money
                if (totalCash > 0.0 && chance <= 0.1) {
                    blorboStealsYourMoneyLoser()
                    makeTextFlash("BlorBo regained power while stealing your money!")
                } else if (totalCash > 0.0 && chance > 0.1) {
                    blorboStealsYourMoneyLoser()
                    makeTextFlash("MONEY STOLEN!")
                }
                //saves data to file
                saveToSaveFile()
                //sets values from save file
                setValuesFromFile()
                //checks for money every 5s
                mainHandler.postDelayed(this, 5000) //5 sec
            }
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // R.id.fragmentContainer = View.GONE

        if (keyCode == KeyEvent.KEYCODE_Q) {
            if (upgradeFragmentVisible) {
                showAllViews()
                upgradeFragmentVisible = false
            } else {
                showAllViews()
            }

            return true // Indicate that the event has been handled
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onBackPressed() {
        if (upgradeFragmentVisible) {
            showAllViews()
            upgradeFragmentVisible = false
        } else {
            super.onBackPressed()
            showAllViews()
        }
    }

    private fun showAllViews() {
                // Make the fragment container and all other views visible
                val rootLayout = findViewById<ViewGroup>(R.id.main)
                for (i in 0 until rootLayout.childCount) {
                    val child = rootLayout.getChildAt(i)
                    child.visibility = View.VISIBLE
                }
               supportFragmentManager.popBackStack()
               supportFragmentManager.isDestroyed
    }
    private fun showUpgradeOptions() {
        if (!upgradeFragmentVisible) {
            val fragment = UpgradeOptionsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
            upgradeFragmentVisible = true
            hideViewsIfUpgradeButtonClicked()
        } else {
            // Hide the upgrade fragment
           // supportFragmentManager.popBackStack()
            // supportFragmentManager.isDestroyed
            upgradeFragmentVisible = false
           // showAllViews()
        }
    }

    private fun hideViewsIfUpgradeButtonClicked() {
        val rootLayout = findViewById<ViewGroup>(R.id.main)

        // Iterate through all child views and set visibility to GONE
        for (i in 0 until rootLayout.childCount) {
            val child = rootLayout.getChildAt(i)
            if (child.id != R.id.fragmentContainer && child.id != R.id.upgradeButton) {
                child.visibility = View.GONE
            }
        }
    }

    fun handleUpgradeOption(option: UpgradeOption) {

    }
    private fun applyUpgradeEffect(option: UpgradeOption) {
        when (option.name) {
//            "Money Laundering Upgrade" -> applyMoneyLaunderingUpgrade(option)
//            "Weapon Upgrade" -> unlockWeaponUpgrade(option)
//            "Autoclicker Upgrade" -> upgradeAutoclicker(option)
//            // Add more cases for other upgrade options if needed
            else -> {
                // Handle unrecognized upgrade options
            }
        }
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
        totalCash -= (blorboMultiplier * totalCash)
        if(totalCash < 0) {
            totalCash = 0.0
        }
        setMoneyBox(cashBox, totalCash)

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

        saveToSaveFile()
        setValuesFromFile()
    }
    fun makeTextFlash(text: String) {
        flashText.setText(text)
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
    fun maybeBlorboGetsMorePowerful(): Double {
        var chance = ((0..100).random()) / 100.0
        System.out.println("Chance: " + chance)
        if (chance <= 0.1) {
            blorboMultiplier = 1.0
            System.out.println("Strength regained!")
        }
        return chance
    }
    fun onMoneyButtonClick(view: View) {
        totalCash += (cashPerClick * clickMultiplier)

        //truncates numbers to two decimal points
        totalCash = Math.round(totalCash * 10.0) / 10.0

        setMoneyBox(cashBox, totalCash)

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

        saveToSaveFile()
        setValuesFromFile()
    }
    fun onUpgradeButtonClick(view: View) {
        if (totalCash >= upgradeCost) {
            totalCash -= upgradeCost
            if (clickMultiplier <= 10) {
                clickMultiplier *= 2
            } else {
                clickMultiplier += upgradeCost / 25
            }
            upgradeCost *= 1.5

            //truncates numbers to two decimal points
            clickMultiplier = Math.round(clickMultiplier * 10.0) / 10.0
            totalCash = Math.round(totalCash * 10.0) / 10.0

            setMultBox(multiplierBox, clickMultiplier)
            setMoneyBox(upgradeCostBox, upgradeCost)
            setMoneyBox(cashBox, totalCash)

            //update text colors
            if (totalCash < upgradeCost) {
                upgradeCostBox.setTextColor(Color.parseColor("#ff0000"))
            }
            if (totalCash < downgradeCost) {
                downgradeCostBox.setTextColor(Color.parseColor("#ff0000"))
            }
        }

        saveToSaveFile()
        setValuesFromFile()
    }
    fun onDowngradeButtonClick(view: View) {
        if (totalCash >= downgradeCost) {
            totalCash -= downgradeCost
            blorboMultiplier /= 1.25
            downgradeCost *= 1.5

            //truncates numbers to two decimal points
            blorboMultiplier = Math.round(blorboMultiplier * 10.0) / 10.0
            totalCash = Math.round(totalCash * 10.0) / 10.0

            setMoneyBox(downgradeCostBox, downgradeCost)
            setMoneyBox(cashBox, totalCash)

            //update text colors
            if (totalCash < upgradeCost) {
                upgradeCostBox.setTextColor(Color.parseColor("#ff0000"))
            }
            if (totalCash < downgradeCost) {
                downgradeCostBox.setTextColor(Color.parseColor("#ff0000"))
            }
        }

        saveToSaveFile()
        setValuesFromFile()
    }
    fun setMoneyBox(box: TextView, value: Double) {
        //truncates numbers to two decimal points
        var valueNew = CompactDecimalFormat.getInstance(Locale.getDefault(), CompactDecimalFormat.CompactStyle.SHORT).format(value)
        Math.round(value * 10.0) / 10.0
        box.text = "$" + valueNew
    }
    fun setMultBox(box: TextView, value: Double) {
        var valueNew = CompactDecimalFormat.getInstance(Locale.getDefault(), CompactDecimalFormat.CompactStyle.SHORT).format(value)
        box.text = "Multiplier: x" + valueNew
    }
    fun saveToSaveFile() {
        val saveDataFile = getSharedPreferences("saveFile", Context.MODE_PRIVATE)
        var saveDataEditor = saveDataFile.edit()

        saveDataEditor.putString("total_cash", totalCash.toString())
        saveDataEditor.putString("cash_per_click", cashPerClick.toString())
        saveDataEditor.putString("click_multiplier", clickMultiplier.toString())
        saveDataEditor.putString("blorbo_multiplier", blorboMultiplier.toString())
        saveDataEditor.putString("downgrade_cost", downgradeCost.toString())
        saveDataEditor.putString("upgrade_cost", upgradeCost.toString())

        saveDataEditor.commit()
    }
    fun setValuesFromFile() {
        var saveFile = getSharedPreferences("saveFile", MODE_PRIVATE)
        totalCash = (saveFile.getString("total_cash", null)?.toDouble() ?: Double) as Double
        cashPerClick = (saveFile.getString("cash_per_click", null)?.toDouble() ?: Double) as Double
        clickMultiplier = (saveFile.getString("click_multiplier", null)?.toDouble() ?: Double) as Double
        blorboMultiplier = (saveFile.getString("blorbo_multiplier", null)?.toDouble() ?: Double) as Double
        downgradeCost = (saveFile.getString("downgrade_cost", null)?.toDouble() ?: Double) as Double
        upgradeCost = (saveFile.getString("upgrade_cost", null)?.toDouble() ?: Double) as Double
    }
    fun onResetButtonClick(view: View) {
        totalCash = 0.0
        cashPerClick = 1.0
        clickMultiplier = 1.0
        blorboMultiplier = 1.0
        downgradeCost = 1.0
        upgradeCost = 1.0

        saveToSaveFile()
        setValuesFromFile()
    }
}