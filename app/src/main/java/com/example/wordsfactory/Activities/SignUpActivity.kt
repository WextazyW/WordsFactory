package com.example.wordsfactory.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wordsfactory.R
import com.google.android.material.button.MaterialButton

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val etName : EditText
        val etEmail : EditText
        val etPassword : EditText

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        etName = findViewById(R.id.et_name)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<MaterialButton>(R.id.btn_next).setOnClickListener {
            try {
                if (etName.text.isNotEmpty() && etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty()){
                    startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                }
                else {
                    Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                }
            } catch (e : Exception){
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}