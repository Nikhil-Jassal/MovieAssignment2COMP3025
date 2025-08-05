package ca.georgian.assignment2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.georgian.assignment2.model.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MovieViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _movies = MutableLiveData<List<Item>>()
    val movies: LiveData<List<Item>> = _movies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadMovies() {
        _isLoading.value = true
        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("users").document(currentUser.uid)
                .collection("movies")
                .get()
                .addOnSuccessListener { result ->
                    val movieList = result.map { doc ->
                        Item(
                            doc.getString("title") ?: "",
                            doc.getString("studio") ?: "",
                            doc.getString("rating") ?: "",
                            doc.getString("year") ?: "",
                            doc.getString("poster") ?: "",
                            doc.getString("description") ?: ""
                        )
                    }
                    _movies.value = movieList
                    _isLoading.value = false
                }
                .addOnFailureListener { e ->
                    _error.value = "Failed to load movies: ${e.message}"
                    _isLoading.value = false
                }
        } else {
            _error.value = "User not authenticated"
            _isLoading.value = false
        }
    }

    fun addMovie(movie: Item) {
        _isLoading.value = true
        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("users").document(currentUser.uid)
                .collection("movies")
                .add(movieToMap(movie))
                .addOnSuccessListener {
                    loadMovies() // Refresh the list
                }
                .addOnFailureListener { e ->
                    _error.value = "Failed to add movie: ${e.message}"
                    _isLoading.value = false
                }
        } else {
            _error.value = "User not authenticated"
            _isLoading.value = false
        }
    }

    fun updateMovie(movie: Item, documentId: String) {
        _isLoading.value = true
        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("users").document(currentUser.uid)
                .collection("movies")
                .document(documentId)
                .set(movieToMap(movie))
                .addOnSuccessListener {
                    loadMovies() // Refresh the list
                }
                .addOnFailureListener { e ->
                    _error.value = "Failed to update movie: ${e.message}"
                    _isLoading.value = false
                }
        } else {
            _error.value = "User not authenticated"
            _isLoading.value = false
        }
    }

    fun deleteMovie(documentId: String) {
        _isLoading.value = true
        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("users").document(currentUser.uid)
                .collection("movies")
                .document(documentId)
                .delete()
                .addOnSuccessListener {
                    loadMovies() // Refresh the list
                }
                .addOnFailureListener { e ->
                    _error.value = "Failed to delete movie: ${e.message}"
                    _isLoading.value = false
                }
        } else {
            _error.value = "User not authenticated"
            _isLoading.value = false
        }
    }

    private fun movieToMap(movie: Item): Map<String, Any> {
        return mapOf(
            "title" to movie.getTitle(),
            "studio" to movie.getStudio(),
            "rating" to movie.getRating(),
            "year" to movie.getYear(),
            "poster" to movie.getPoster(),
            "description" to movie.getDescription()
        )
    }
}