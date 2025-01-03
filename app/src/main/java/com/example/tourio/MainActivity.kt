package com.example.tourio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         replacementFragment(HomeFragment())

         findViewById<BottomNavigationView>(R.id.bottom_menue).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homepage -> replacementFragment(HomeFragment())
                R.id.feedbackpage -> replacementFragment(HotelFragment())

                else -> false
            }
            true
        }
    }

     private fun replacementFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
