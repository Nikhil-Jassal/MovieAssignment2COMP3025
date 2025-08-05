package ca.georgian.assignment2.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ca.georgian.assignment2.R
import ca.georgian.assignment2.databinding.ActivityLoginBinding
import ca.georgian.assignment2.ui.MovieListActivity
import ca.georgian.assignment2.viewmodels.AuthViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (validateInputs(email, password)) {
                authViewModel.loginUser(email, password).observe(this) { result ->
                    when (result) {
                        is Resource.Success -> {
                            startActivity(Intent(this, MovieListActivity::class.java))
                            finish()
                        }
                        is Resource.Error -> {
                            showErrorAlert(result.message ?: "Login failed")
                        }
                    }
                }
            }
        }

        binding.tvRegisterLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun showErrorAlert(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Login Error")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}