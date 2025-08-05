package ca.georgian.assignment2.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class MovieRepository {
    private val db: FirebaseFirestore = Firebase.firestore
    private val auth = Firebase.auth

    fun getUserMovies(): Flow<List<Movie>> = flow {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            emit(emptyList())
            return@flow
        }

        val snapshot = db.collection("movies")
            .whereEqualTo("userId", currentUser.uid)
            .get()
            .await()

        val movies = snapshot.documents.map { doc ->
            doc.toObject(Movie::class.java)?.copy(id = doc.id) ?: Movie()
        }
        emit(movies)
    }

    suspend fun addMovie(movie: Movie) {
        val currentUser = auth.currentUser ?: return
        val movieWithUser = movie.copy(userId = currentUser.uid)
        db.collection("movies").add(movieWithUser).await()
    }

    suspend fun updateMovie(movie: Movie) {
        if (movie.id.isEmpty()) return
        db.collection("movies").document(movie.id).set(movie).await()
    }

    suspend fun deleteMovie(movieId: String) {
        if (movieId.isEmpty()) return
        db.collection("movies").document(movieId).delete().await()
    }
}