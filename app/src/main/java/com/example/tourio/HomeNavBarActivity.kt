package com.example.tourio

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeNavBarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homenavbar)


        replacementFragment(HomeFragment())

        findViewById<BottomNavigationView>(R.id.bottom_menue).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navbarhomepage -> replacementFragment(HomeFragment())
                R.id.navbarrequest -> replacementFragment(RequestFragment())
                R.id.navbarhotel -> replacementFragment(HotelFragment())
                R.id.navbarprofile -> replacementFragment(ProfileFragment())

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