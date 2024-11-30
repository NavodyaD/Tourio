package com.example.tourio

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val registerBtn: Button = findViewById(R.id.signupbtn)
        val name: EditText = findViewById(R.id.name)
        val age: EditText = findViewById(R.id.age)
        val country: EditText = findViewById(R.id.country)
        val email: EditText = findViewById(R.id.emailId)
        val password: EditText = findViewById(R.id.passwordId)
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)

        registerBtn.setOnClickListener {
            val nameText = name.text.toString().trim()
            val ageText = age.text.toString().trim()
            val countryText = country.text.toString().trim()
            val emailText = email.text.toString().trim()
            val passText = password.text.toString().trim()

            val selectedRadioId = radioGroup.checkedRadioButtonId
            val userType = when (selectedRadioId) {
                R.id.travelerRadioButton -> "Traveler"
                R.id.guideRadioButton -> "Guide"
                R.id.hotelRadioButton -> "Hotel"
                else -> null
            }

            if (nameText.isEmpty() || ageText.isEmpty() || countryText.isEmpty() || emailText.isEmpty() || passText.isEmpty() || userType == null) {
                Toast.makeText(this, "Please fill out all fields and select a type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (ageText.toIntOrNull() == null || ageText.toInt() <= 0) {
                Toast.makeText(this, "Please enter a valid age", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Log the registration data
            Log.i("Registration Data", "Name: $nameText")
            Log.i("Registration Data", "Age: $ageText")
            Log.i("Registration Data", "Country: $countryText")
            Log.i("Registration Data", "Email: $emailText")
            Log.i("Registration Data", "Password: $passText")
            Log.i("Registration Data", "Type: $userType")

            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            sharedPreferences.edit().apply {
                putString("Name", nameText)
                putString("Age", ageText)
                putString("Country", countryText)
                putString("Email", emailText)
                putString("Type", userType)
                apply()
            }

            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("email", emailText)
            intent.putExtra("type", userType)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart Called!")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume Called!")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause Called!")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop Called!")
    }
}
