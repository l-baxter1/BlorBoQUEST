package com.zybooks.blorboquest

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlin.concurrent.timer
import kotlin.properties.Delegates
import kotlin.system.measureTimeMillis


class Mail: AppCompatActivity() {
     var textId by Delegates.notNull<Int>()
    private val textViewsArray = arrayOfNulls<TextView>(10)
    private var counter = 0
    private val handler = Handler(Looper.getMainLooper())
    lateinit var timerTextView: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail)
        timerTextView=findViewById(R.id.timer)
        supportActionBar?.setIcon(R.drawable.menu_icon)
        setSupportActionBar(findViewById(R.id.nav_menu))
        val updateCounterRunnable = object : Runnable {
            override fun run() {
                counter++
                timerTextView.text = "Time: $counter"
                handler.postDelayed(this, 1000) // Update every 10 second
                when (counter) {
                    10 -> addEmail(0)
                    20 -> addEmail(1)
                    30 -> addEmail(2)
                    40 -> addEmail(3)
                    50 -> addEmail(4)
                    60 -> addEmail(5)
                    70 -> addEmail(6)
                    80 -> addEmail(7)
                    90 -> addEmail(8)
                    100 -> addEmail(9)

                }
            }
        }
        handler.post(updateCounterRunnable)






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
    @RequiresApi(Build.VERSION_CODES.O)
    fun addEmail(ID:Int) {
        val parentLayout = findViewById<LinearLayout>(R.id.email_layout)
        val customFont = resources.getFont(R.font.press_start_2p)

        val stringArray = resources.getStringArray(R.array.emailLabelArray)

            textViewsArray[ID] = TextView(this)
            textViewsArray[ID]?.text = stringArray[ID]
            textViewsArray[ID]?.typeface=customFont
            textViewsArray[ID]?.maxLines=1
            textViewsArray[ID]?.textSize= 10F

            // You can customize other properties of the TextView here

            parentLayout.addView(textViewsArray[ID])
        textViewsArray[0]?.setOnClickListener{

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, Email())
                .addToBackStack(null)
                .commit()
            textId=0
        }
        textViewsArray[1]?.setOnClickListener{

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, Email())
                .addToBackStack(null)
                .commit()
            textId=1
        }
        textViewsArray[2]?.setOnClickListener{

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, Email())
                .addToBackStack(null)
                .commit()
            textId=2
        }
        textViewsArray[3]?.setOnClickListener{

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, Email())
                .addToBackStack(null)
                .commit()
            textId=3
        }
        textViewsArray[4]?.setOnClickListener{

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, Email())
                .addToBackStack(null)
                .commit()
            textId=4
        }
        textViewsArray[5]?.setOnClickListener{

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, Email())
                .addToBackStack(null)
                .commit()
            textId=5
        }
        textViewsArray[6]?.setOnClickListener{

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, Email())
                .addToBackStack(null)
                .commit()
            textId=6
        }
        textViewsArray[7]?.setOnClickListener{

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, Email())
                .addToBackStack(null)
                .commit()
            textId=7
        }
        textViewsArray[8]?.setOnClickListener{

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, Email())
                .addToBackStack(null)
                .commit()
            textId=8
        }
        textViewsArray[9]?.setOnClickListener{

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, Email())
                .addToBackStack(null)
                .commit()
            textId=9
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



