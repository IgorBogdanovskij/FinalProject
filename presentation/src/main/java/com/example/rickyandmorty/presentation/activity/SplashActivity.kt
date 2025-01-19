package com.example.rickyandmorty.presentation.activity

import android.content.Intent
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.rickyandmorty.R
import dagger.Component

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splesh)

        val rickMortyImg = findViewById<ImageView>(R.id.img_RickMorty)
        rickMortyImg.alpha = 0f
        rickMortyImg.animate().setDuration(1500).alpha(1f).withEndAction {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
