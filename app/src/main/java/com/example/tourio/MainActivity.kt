package com.example.tourio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_getstarted)

        val getStartedButton: Button = findViewById(R.id.getstarted_button)
        getStartedButton.setOnClickListener {
           val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

}
