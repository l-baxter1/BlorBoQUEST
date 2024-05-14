package com.zybooks.blorboquest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class Mail: AppCompatActivity() {
    private var textId = 9
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail)


        supportActionBar?.setIcon(R.drawable.menu_icon)
        setSupportActionBar(findViewById(R.id.nav_menu))
        addEmail(1)

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
    fun addEmail(ID:Int) {
        val parentLayout = findViewById<LinearLayout>(R.id.email_layout)
        val textViewsArray = arrayOfNulls<TextView>(10)
        val stringArray = resources.getStringArray(R.array.emailLabelArray)
        for (i in textViewsArray.indices) {
            textViewsArray[i] = TextView(this)
            textViewsArray[i]?.text = stringArray[i]
            // You can customize other properties of the TextView here
        }



        for (i in textViewsArray.indices) {
            parentLayout.addView(textViewsArray[i])
        }

    }
//        val textView = TextView(this)
//        textView.text =
//            "BlorBo                         kill yourself                          today"
//        val params = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            LinearLayout.LayoutParams.MATCH_PARENT
//        )
//        textView.layoutParams = params
//        myLinearLayout.addView(textView)

    }

//    fun openFragment(view: View) {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container_view, Email())
//            .addToBackStack(null)
//            .commit()
//    }



