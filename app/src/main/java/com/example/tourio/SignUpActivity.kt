package com.example.tourio

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val userRoleRadioGroup: RadioGroup = findViewById(R.id.userRoleRadioGroup)
        val nameField: EditText = findViewById(R.id.signNameText)
        val ageField: EditText = findViewById(R.id.signAgeText)
        val countryField: EditText = findViewById(R.id.signCountryText)
        val emailField: EditText = findViewById(R.id.signEmailText)
        val passwordField: EditText = findViewById(R.id.signPasswordText)
        val signupButton: Button = findViewById(R.id.buttonSignUp)

        signupButton.setOnClickListener {
            val selectedRoleId = userRoleRadioGroup.checkedRadioButtonId
            val userRole = findViewById<RadioButton>(selectedRoleId)?.text.toString()
            val name = nameField.text.toString()
            val age = ageField.text.toString()
            val country = countryField.text.toString()
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            if (name.isNotEmpty() && age.isNotEmpty() && country.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                registerUser(userRole, name, age, country, email, password)
            } else {
                Toast.makeText(this, "Please fill all the details to sign up", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(userRole: String, name: String, age: String, country: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    val user = mapOf(
                        "userRole" to userRole,
                        "name" to name,
                        "age" to age,
                        "country" to country,
                        "email" to email
                    )

                    if (userId != null) {
                        firestore.collection("Users")
                            .document(userId)
                            .set(user, SetOptions.merge())
                            .addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) {
                                    val currentUserRef = firestore.collection("Users").document(userId)
                                    currentUserRef.get()
                                        .addOnSuccessListener { document ->
                                            if (document.exists()) {
                                                val userRole = document.getString("userRole")
                                                val userName = document.getString("name")

                                                when (userRole) {
                                                    "Traveler" -> {
                                                        Toast.makeText(this, "Welcome, $userName!", Toast.LENGTH_SHORT).show()
                                                        val intent = Intent(this, HomeNavBarActivity::class.java)
                                                        startActivity(intent)
                                                        finish()
                                                    }
                                                    "Guide" -> {
                                                        Toast.makeText(this, "Welcome, $userName!", Toast.LENGTH_SHORT).show()
                                                        //val intent = Intent(this, GuideProfileActivity::class.java)
                                                        //startActivity(intent)
                                                        //finish()
                                                    }
                                                    "Hotel" -> {
                                                        Toast.makeText(this, "Welcome, $userName!", Toast.LENGTH_SHORT).show()
                                                        val intent = Intent(this, HotelSignFormActivity::class.java)
                                                        startActivity(intent)
                                                        finish()
                                                    }
                                                    else -> {
                                                        Toast.makeText(this, "User role not recognized.", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            }
                                        }
                                } else {
                                    Toast.makeText(this, "Failed to create account: ${firestoreTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                } else {
                    Toast.makeText(this, "Sign up failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun navigateToLogin(view: View)
    {
        val intent = Intent(this, LoginPageActivity::class.java)
        startActivity(intent)
        finish()
    }
}
