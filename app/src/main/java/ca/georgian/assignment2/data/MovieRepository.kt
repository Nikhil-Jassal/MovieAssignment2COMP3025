package ca.georgian.assignment2.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class MovieRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun getMovies(): List<Movie> {
        val currentUser = auth.currentUser
        return if (currentUser != null) {
            try {
                db.collection("movies")
                    .whereEqualTo("userId", currentUser.uid)
                    .orderBy("title", Query.Direction.ASCENDING)
                    .get()
                    .await()
                    .toObjects(Movie::class.java)
            } catch (e: Exception) {
                Log.e("MovieRepository", "Error getting movies", e)
                emptyList()
            }
        } else {
            emptyList()
        }
    }

    suspend fun addMovie(movie: Movie) {
        try {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val movieWithUser = movie.copy(userId = currentUser.uid)
                db.collection("movies").add(movieWithUser).await()
            }
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error adding movie", e)
            throw e
        }
    }

    suspend fun updateMovie(movie: Movie) {
        try {
            db.collection("movies").document(movie.id).set(movie).await()
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error updating movie", e)
            throw e
        }
    }

    suspend fun deleteMovie(movieId: String) {
        try {
            db.collection("movies").document(movieId).delete().await()
        } catch (e: Exception) {
            Log.e("MovieRepository", "Error deleting movie", e)
            throw e
        }
    }
}