package ca.georgian.assignment2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Authenticated(val userId: String) : AuthState()
        object Unauthenticated : AuthState()
    }

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        auth.addAuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                _authState.value = AuthState.Authenticated(firebaseAuth.currentUser!!.uid)
            } else {
                _authState.value = AuthState.Unauthenticated
            }
        }
    }

    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated(auth.currentUser!!.uid)
                } else {
                    _error.value = task.exception?.message ?: "Login failed"
                    _authState.value = AuthState.Unauthenticated
                }
            }
    }

    fun register(email: String, password: String) {
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated(auth.currentUser!!.uid)
                } else {
                    _error.value = task.exception?.message ?: "Registration failed"
                    _authState.value = AuthState.Unauthenticated
                }
            }
    }

    fun logout() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
}