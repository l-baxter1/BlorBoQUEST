package com.zybooks.blorboquest

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuPage  : AppCompatActivity() {
    private lateinit var emailNavButton: Button
    private var emailNotificationNum = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_page)
        emailNavButton=findViewById(R.id.email_nav)

    }
fun onEmailButtonClicked(view: View) {
    emailNotificationNum++
    var temp= emailNotificationNum.toString()
    temp= "Email ($temp) > "
    emailNavButton.text=temp
}
}