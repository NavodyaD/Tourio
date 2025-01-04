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


        //replacementFragment(HomeFragment())

        //findViewById<BottomNavigationView>(R.id.bottom_menue).setOnItemSelectedListener { item ->
            //when (item.itemId) {
                //R.id.homepage -> replacementFragment(HomeFragment())
                //R.id.hotel -> replacementFragment(HotelFragment())
                //R.id.request -> replacementFragment(RequestFragment())
                //R.id.profile -> replacementFragment(ProfileFragment())



               // else -> false
            //}
            //true
        //}
    }

    //private fun replacementFragment(fragment: Fragment) {
        //val fragmentTransaction = supportFragmentManager.beginTransaction()
        //fragmentTransaction.replace(R.id.frame_layout, fragment)
        //fragmentTransaction.commit()
    //}
}
