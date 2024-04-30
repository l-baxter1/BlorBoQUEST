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
    private lateinit var cashTextBox: TextView
    private lateinit var background: ConstraintLayout
    private var totalCash = 0
    private var cashPerClick = 1
    private var clickMultiplier = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setIcon(R.drawable.menu_icon)
        setSupportActionBar(findViewById(R.id.nav_menu))
        moneyButton = findViewById(R.id.moneyButton)
        cashTextBox = findViewById(R.id.cashNumber)
        background = findViewById(R.id.main)

        background.background = getDrawable(R.drawable.placeholder_bg)
        moneyButton.background = getDrawable(R.drawable.button_default)

        cashTextBox.text = totalCash.toString()
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
        cashTextBox.text = totalCash.toString()
    }

    fun onUpgradeButtonClick(view: View) {
        clickMultiplier *= 2
    }
}