package ca.georgian.assignment2.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MovieRepository {
    private val db = FirebaseFirestore.getInstance()
    private val moviesCollection = db.collection("movies")

    fun getAllMovies(): LiveData<List<Movie>>
    {
        val moviesLiveData = MutableLiveData<List<Movie>>()

        moviesCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                // Handle error
                return@addSnapshotListener
            }

            val moviesList = mutableListOf<Movie>()
            snapshot?.documents?.forEach { document ->
                document.toObject(Movie::class.java)?.let { movie ->
                    movie.id = document.id
                    moviesList.add(movie)
                }
            }
            moviesLiveData.value = moviesList
        }

        return moviesLiveData
    }

    fun addMovie(movie: Movie) {
        moviesCollection.add(movie)
    }

    fun updateMovie(movie: Movie) {
        movie.id?.let { id ->
            moviesCollection.document(id).set(movie)
        }
    }

    fun deleteMovie(movieId: String) {
        moviesCollection.document(movieId).delete()
    }
}