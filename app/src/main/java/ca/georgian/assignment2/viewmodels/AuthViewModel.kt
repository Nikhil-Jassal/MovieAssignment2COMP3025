package ca.georgian.assignment2.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class AuthResult {
    object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _loginResult = MutableStateFlow<AuthResult?>(null)
    val loginResult: StateFlow<AuthResult?> = _loginResult

    private val _registerResult = MutableStateFlow<AuthResult?>(null)
    val registerResult: StateFlow<AuthResult?> = _registerResult

    fun login(email: String, password: String) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            _loginResult.value = AuthResult.Success
        } catch (e: Exception) {
            _loginResult.value = AuthResult.Error(e.message ?: "Login failed")
        }
    }

    fun register(email: String, password: String) = viewModelScope.launch {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            _registerResult.value = AuthResult.Success
        } catch (e: Exception) {
            _registerResult.value = AuthResult.Error(e.message ?: "Registration failed")
        }
    }
}