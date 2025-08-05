package ca.georgian.assignment2.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ca.georgian.assignment2.databinding.ActivityLoginBinding
import ca.georgian.assignment2.ui.MovieListActivity
import ca.georgian.assignment2.viewmodels.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Direct login button click listener with hardcoded values (for testing)
        binding.btnLogin.setOnClickListener {
            // For testing (like in the example), we could use:
            // signIn(email = "test@auth.com", password = "password")

            // For production, use actual user input:
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (validateInputs(email, password)) {
                signIn(email, password)
            }
        }

        binding.tvRegisterLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    Log.d(TAG, "signInWithEmail:success")
                    startActivity(Intent(this, MovieListActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed: ${task.exception?.message}",
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
        return true
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}