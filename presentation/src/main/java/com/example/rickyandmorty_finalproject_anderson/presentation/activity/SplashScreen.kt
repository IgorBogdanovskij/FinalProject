package com.example.rickyandmorty_finalproject_anderson.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.rickyandmorty_finalproject_anderson.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splesh_screen)

        val rickMortyImg = findViewById<ImageView>(R.id.img_RickMorty)
        rickMortyImg.alpha = 0f
        rickMortyImg.animate().setDuration(1500).alpha(1f).withEndAction {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}