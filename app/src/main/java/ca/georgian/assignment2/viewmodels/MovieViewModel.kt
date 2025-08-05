package ca.georgian.assignment2.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ca.georgian.assignment2.data.Movie
import ca.georgian.assignment2.data.MovieRepository

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MovieRepository()
    val movies: LiveData<List<Movie>> = repository.getAllMovies()

    fun addMovie(movie: Movie) {
        repository.addMovie(movie)
    }

    fun updateMovie(movie: Movie) {
        repository.updateMovie(movie)
    }

    fun deleteMovie(movieId: String) {
        repository.deleteMovie(movieId)
    }
}