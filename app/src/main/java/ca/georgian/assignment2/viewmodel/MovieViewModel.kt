package ca.georgian.assignment2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.georgian.assignment2.data.Movie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MovieViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _movies = MutableLiveData<List<Pair<Movie, String>>>()
    val movies: LiveData<List<Pair<Movie, String>>> = _movies

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
                        Pair(
                            Movie(
                                id = doc.id,
                                title = doc.getString("title") ?: "",
                                studio = doc.getString("studio") ?: "",
                                rating = doc.getString("rating") ?: "",
                                year = doc.getString("year") ?: "",
                                poster = doc.getString("poster") ?: "",
                                posterUrl = doc.getString("posterUrl") ?: "",
                                description = doc.getString("description") ?: "",
                                userId = doc.getString("userId") ?: currentUser.uid
                            ),
                            doc.id
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

    fun addMovie(movie: Movie) {
        _isLoading.value = true
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val movieWithUserId = movie.copy(userId = currentUser.uid)
            db.collection("users").document(currentUser.uid)
                .collection("movies")
                .add(movieToMap(movieWithUserId))
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

    fun updateMovie(documentId: String, movie: Movie) {
        _isLoading.value = true
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val movieWithUserId = movie.copy(userId = currentUser.uid)
            db.collection("users").document(currentUser.uid)
                .collection("movies")
                .document(documentId)
                .set(movieToMap(movieWithUserId))
                .addOnSuccessListener {
                    // Refresh the list
                    loadMovies()
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
                    // Refresh the list
                    loadMovies()
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

    private fun movieToMap(movie: Movie): Map<String, Any> {
        return mapOf(
            "id" to movie.id,
            "title" to movie.title,
            "studio" to movie.studio,
            "rating" to movie.rating,
            "year" to movie.year,
            "poster" to movie.poster,
            "posterUrl" to movie.posterUrl,
            "description" to movie.description,
            "userId" to movie.userId
        )
    }
}