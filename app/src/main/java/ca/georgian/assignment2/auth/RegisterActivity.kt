package ca.georgian.assignment2.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ca.georgian.assignment2.databinding.ActivityRegisterBinding
import ca.georgian.assignment2.viewmodels.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Direct registration button click listener
        binding.btnRegister.setOnClickListener {
            // Get email and password from input fields
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            // For testing purposes (like in the example), you could use hardcoded values:
            // registerUser(email = "test@auth.com", password = "password")

            // But for production, use the actual user input:
            if (validateInputs(email, password)) {
                registerUser(email, password)
            }
        }

        binding.tvLoginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign up success
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(
                        baseContext, "Registration successful.",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    // If sign up fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Registration failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.etEmail.error = "Email is required"
            return false
        }
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required"
            return false
        }
        if (password.length < 6) {
            binding.etPassword.error = "Password must be at least 6 characters"
            return false
        }
        return true
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}