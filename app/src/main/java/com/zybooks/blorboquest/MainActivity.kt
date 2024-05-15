package com.zybooks.blorboquest

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.icu.text.CompactDecimalFormat
import android.media.Image
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
import android.widget.Toast
import java.util.Locale
import android.widget.ImageView
import android.media.MediaPlayer


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
    private lateinit var BlorboDeadImage: ImageView
    private lateinit var blorboMove:ImageView
    private lateinit var blorboLaugh: ImageView
    private lateinit var stinkManDead: ImageView
    private lateinit var stinkMan: ImageView
    private lateinit var black: ImageView

    private var autoclickersCount = 0
    private lateinit var autoclickerHandler: Handler

    var totalCash = 0.0
    var cashPerClick = 1.0
    var clickMultiplier = 1.0
    var blorboMultiplier = 1.0
    var downgradeCost = 1.0
    var upgradeCost = 1.0

    private lateinit var killButton: Button
    private var killUnlocked = false
    private lateinit var resultsText: TextView
    private var blorboMoney = 30000.0

    private var upgradeFragmentVisible = false

    private lateinit var mediaPlayer: MediaPlayer

    private val upgradeOptions = listOf(
        UpgradeOption("Money Laundering Upgrade", 400, "Upgrades money multiplier by x30"),
        UpgradeOption("Weapon Upgrade", 5000, "Unlocks an ending but that's coded later"),
        UpgradeOption("Autoclicker Upgrade", 20, "Adds 1 automatic click per upgrade")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        killButton = findViewById(R.id.killButton)
        resultsText = findViewById(R.id.resultsText)
        BlorboDeadImage = findViewById(R.id.blorboDeadImage)
        blorboMove = findViewById(R.id.blorboMove)
        blorboLaugh = findViewById(R.id.blorboEvilLaughImage)
        stinkManDead = findViewById(R.id.stinkDeadImage)
        stinkMan = findViewById(R.id.emotionImage)
        black = findViewById(R.id.deadImage)

        background.background = getDrawable(R.drawable.placeholder_bg)
        flashText.visibility = View.GONE

        setMoneyBox(cashBox, totalCash)
        setMoneyBox(upgradeCostBox, upgradeCost)
        setMoneyBox(downgradeCostBox, downgradeCost)
        setMultBox(multiplierBox, clickMultiplier)

        upgradeButton.setOnClickListener {
            showUpgradeOptions()
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.backgroundmusiclong)

        // Set looping to true for continuous playback
        mediaPlayer.isLooping = true

        // Start playing the audio
        mediaPlayer.start()

        killButton.setOnClickListener {
            // Check if the player has over 30000 dollars
            if (totalCash >= 3000) {
                // Player wins
                findViewById<TextView>(R.id.resultsText).apply {
                    visibility = View.VISIBLE
                    text = "YOU WIN"
                    BlorboDeadImage.visibility = View.VISIBLE
                    blorboMove.visibility = View.GONE
                    stinkMan.visibility = View.GONE
                    moneyButton.visibility = View.GONE
                    killButton.visibility = View.GONE
                    black.visibility = View.VISIBLE
                }
            } else {
                // Player loses
                findViewById<TextView>(R.id.resultsText).apply {
                    visibility = View.VISIBLE
                    text = "WOMP WOMP,YOU DIED "
                    killButton.visibility = View.GONE
                    blorboMove.visibility = View.GONE
                    stinkMan.visibility = View.GONE
                    moneyButton.visibility = View.GONE
                    stinkManDead.visibility = View.VISIBLE
                    blorboLaugh.visibility = View.VISIBLE
                    black.visibility = View.VISIBLE
                }
            }
        }

        killButton.visibility = View.GONE
        resultsText.visibility = View.GONE
        BlorboDeadImage.visibility = View.GONE

        autoclickerHandler = Handler(Looper.getMainLooper())

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
            BlorboDeadImage.visibility = View.GONE
            blorboLaugh.visibility = View.GONE
            stinkManDead.visibility = View.GONE
            black.visibility = View.GONE
            return true // Indicate that the event has been handled
        }
        killButton.visibility = View.VISIBLE
        if (killUnlocked == false) {
            R.id.killButton = View.VISIBLE
        } else {
            R.id.killButton = View.GONE
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
        if (killUnlocked == true) {
            killButton.visibility = View.VISIBLE
        } else {
            killButton.visibility = View.GONE
        }
        resultsText.visibility = View.GONE
        R.id.blorboDeadImage = View.GONE
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

    // Call startAutoclicker() when the autoclicker is purchased
    private fun startAutoclicker() {
        autoclickerHandler.postDelayed(object : Runnable {
            override fun run() {
                // Increment total cash by 1 and update the UI
                totalCash += 1
                setMoneyBox(cashBox, totalCash)

                // Repeat the autoclicker every second
                autoclickerHandler.postDelayed(this, 1000)
            }
        }, 1000)
    }

    // Call stopAutoclicker() when the autoclicker is disabled or removed
    private fun stopAutoclicker() {
        autoclickerHandler.removeCallbacksAndMessages(null)
    }

    fun handleUpgradeOption(option: UpgradeOption) {
        applyUpgradeEffect(option)
    }
    private fun applyUpgradeEffect(option: UpgradeOption) {
        when (option.name) {
            "Money Laundering Upgrade" -> applyMoneyLaunderingUpgrade(option)
            //"Weapon Upgrade" -> unlockWeaponUpgrade(option)
            "Autoclicker Upgrade" -> buyAutoclicker(option)
            // Add more cases for other upgrade options if needed
            "Weapon Upgrade" -> unlockWeaponUpgrade(option)
            else -> {
                // Handle unrecognized upgrade options
            }
        }
    }
    fun unlockWeaponUpgrade(option: UpgradeOption) {
        // Show the kill button when the weapon upgrade is bought
        var cost = option.cost

        if (totalCash >= cost) {
            totalCash -= cost
            killButton.visibility = View.VISIBLE
            killUnlocked == true
        }
    }

    private fun buyAutoclicker(option: UpgradeOption) {
        var cost = option.cost

        if (totalCash >= cost) {
            clickMultiplier += 2.0
            // Update UI to reflect the new multiplier and total cash
            setMultBox(multiplierBox, clickMultiplier)
            setMoneyBox(cashBox, totalCash)
            totalCash -= cost
            cost *= cost
            startAutoclicker()
            autoclickersCount++
            // Increment the total cash by 1 for each autoclicker
            mainHandler.postDelayed({
                totalCash += autoclickersCount
                setMoneyBox(cashBox, totalCash)
            }, 1000) // Adjust the delay (in milliseconds) as needed
        } else {
            // Handle case where player doesn't have enough cash to buy the upgrade
            // You might show a message to the player indicating insufficient funds
        }
    }
    private fun applyMoneyLaunderingUpgrade(option: UpgradeOption) {
        if (totalCash >= 5000.0) {
            // Deduct $100 from the total cash
            totalCash -= 5000.0
            // Apply the money laundering multiplier
            clickMultiplier *= 5.0
            // Update UI to reflect the new multiplier and total cash
            setMultBox(multiplierBox, clickMultiplier)
            setMoneyBox(cashBox, totalCash)
            // Update text colors
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
        } else {
            // Handle case where player doesn't have enough cash to buy the upgrade
            // You might show a message to the player indicating insufficient funds
        }
    }
    fun onKillButtonClick(view: View) {
        if (totalCash > blorboMoney) {
            // Player wins if their money is above BlorBo's money
            resultsText.text = "YOU WON"
        } else {
            // Player loses if their money is not above BlorBo's money
            resultsText.text = "WOMP WOMP. :( YOU DIED"
        }
        resultsText.visibility = View.VISIBLE
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

        saveDataEditor.apply()
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
}