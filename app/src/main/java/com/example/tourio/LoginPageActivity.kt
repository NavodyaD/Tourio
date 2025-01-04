package com.example.tourio

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginPageActivity : AppCompatActivity() {

    // Firebase instance
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginpage)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()

        // Get references to input fields and button
        val emailField: EditText = findViewById(R.id.emailId)
        val passwordField: EditText = findViewById(R.id.passwordId)
        val loginButton: Button = findViewById(R.id.loginBtn)

        // Login button click
        loginButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            }
            else
            {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUserId = auth.currentUser?.uid
                    if (currentUserId != null) {
                        // created the firestore reference
                        val db = FirebaseFirestore.getInstance()
                        val currentUserRef = db.collection("Users").document(currentUserId)

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
                                            val intent = Intent(this, HomePageActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                        "Hotel" -> {
                                            Toast.makeText(this, "Welcome, $userName!", Toast.LENGTH_SHORT).show()
                                            val intent = Intent(this, HotelDashBoardActivity::class.java)
                                            intent.putExtra("userId", currentUserId)
                                            startActivity(intent)
                                            finish()
                                        }
                                        else -> {
                                            Toast.makeText(this, "User role not recognized.", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                } else {
                                    Toast.makeText(this, "User details not found.", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .addOnFailureListener { exception ->
                                Toast.makeText(this, "Failed to fetch user details: ${exception.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "Failed to get user ID.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

}

