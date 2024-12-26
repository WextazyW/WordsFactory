package com.example.wordsfactory.Activities.intro

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wordsfactory.Activities.MainActivity
import com.example.wordsfactory.Activities.SignUpActivity
import com.example.wordsfactory.R
import com.google.android.material.button.MaterialButton

class IntroThreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intro_three)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<MaterialButton>(R.id.btn_skip).setOnClickListener {
            startActivity(Intent(this@IntroThreeActivity, SignUpActivity::class.java))
        }
    }
}