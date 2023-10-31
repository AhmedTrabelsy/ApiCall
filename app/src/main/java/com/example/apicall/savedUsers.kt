package com.example.apicall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class savedUsers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_users)
        var name = intent.getStringExtra("name")
        var username = intent.getStringExtra("username")
        var email = intent.getStringExtra("email")

        val tv_name: TextView = findViewById(R.id.name)
        val tv_username: TextView = findViewById(R.id.username)
        val tv_email: TextView = findViewById(R.id.email)

        tv_name.text = name
        tv_username.text = username
        tv_email.text = email
    }
}