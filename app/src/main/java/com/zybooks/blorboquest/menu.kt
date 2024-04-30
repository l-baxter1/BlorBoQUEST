package com.zybooks.blorboquest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MenuPage  : AppCompatActivity() {
    private lateinit var emailNavButton: Button
    private lateinit var settingsNavButton: Button
    private lateinit var backgroundNInfoNavButton: Button
    private lateinit var mainPageNavButton: Button
    private lateinit var galleryNavButton: Button
    private lateinit var creditsNavButton: Button
    private var emailNotificationNum = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_page)
        emailNavButton=findViewById(R.id.email_nav)
        settingsNavButton=findViewById(R.id.settings_nav)
        backgroundNInfoNavButton=findViewById(R.id.background_nav)
        mainPageNavButton=findViewById(R.id.main_nav)
        galleryNavButton=findViewById(R.id.gallery_nav)
        creditsNavButton=findViewById(R.id.credits_nav)
    }
    fun onEmailButtonClicked(view: View) {
        emailNotificationNum++
        var temp= emailNotificationNum.toString()
        temp= "Email ($temp) > "
        emailNavButton.text=temp
        startActivity(Intent(this, Mail::class.java))
    }
    fun onBackgroundInfoButtonClicked(view: View){
        startActivity(Intent(this, BackgroundInfo::class.java))
    }
    fun onSettingsButtonClicked(view: View){
        startActivity(Intent(this, Settings::class.java))
    }
    fun onGalleryButtonClicked(view: View){
        startActivity(Intent(this, Gallery::class.java))
    }
    fun onCreditsButtonClicked(view: View){
        startActivity(Intent(this, Credits::class.java))
    }
    fun onMainPageButtonClicked(view: View){
        startActivity(Intent(this, MainActivity::class.java))
    }
}