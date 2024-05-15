package com.zybooks.blorboquest

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Gallery: AppCompatActivity() {
    private lateinit var rightButton: Button
    private lateinit var leftButton: Button
    private lateinit var galleryImage:ImageView
    var galIndex = 0
    val galleryImages = arrayOf(
        R.drawable.gallery1,
        R.drawable.gallery2,
        R.drawable.gallery3,
        R.drawable.gallery4,
        R.drawable.gallery5,
        R.drawable.gallery6,
        R.drawable.gallery7,
        R.drawable.gallery8,
        R.drawable.gallery9,
        R.drawable.gallery10,
        R.drawable.gallery11
    )
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        rightButton = findViewById(R.id.right_button)
        leftButton = findViewById(R.id.left_button)
        galleryImage = findViewById(R.id.galleryImageView)


        supportActionBar?.setIcon(R.drawable.menu_icon)
        setSupportActionBar(findViewById(R.id.nav_menu))
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
    fun onRightClicked(view: View) {
        galIndex++
        if (galIndex > galleryImages.size-1){
            galIndex = 0
        }
        galleryImage.setImageResource(galleryImages[galIndex])

    }
    fun onLeftClicked(view: View) {
        galIndex--
        if(galIndex == 0){
            galIndex = galleryImages.size-1
        }
        galleryImage.setImageResource(galleryImages[galIndex])
    }
}