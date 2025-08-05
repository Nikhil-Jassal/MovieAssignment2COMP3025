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

    // Sample movie data
    private val sampleMovies = listOf(
        Movie(
            id = "",
            title = "The Shawshank Redemption",
            year = "1994",
            rating = "9.3",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BNDE3ODcxYzMtY2YzZC00NmNlLWJiNDMtZDViZWM2MzIxZDYwXkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_.jpg",
            description = "Two imprisoned men bond over a number of years...",
            userId = "",
            studio = "Castle Rock Entertainment",
            poster = "https://m.media-amazon.com/images/M/MV5BNDE3ODcxYzMtY2YzZC00NmNlLWJiNDMtZDViZWM2MzIxZDYwXkEyXkFqcGdeQXVyNjAwNDUxODI@._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "The Godfather",
            year = "1972",
            rating = "9.2",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
            description = "The aging patriarch of an organized crime dynasty...",
            userId = "",
            studio = "Paramount Pictures",
            poster = "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "The Dark Knight",
            year = "2008",
            rating = "9.0",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_.jpg",
            description = "When the menace known as the Joker wreaks havoc...",
            userId = "",
            studio = "Warner Bros.",
            poster = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "Pulp Fiction",
            year = "1994",
            rating = "8.9",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BNGNhMDIzZTUtNTBlZi00MTRlLWFjM2ItYzViMjE3YzI5MjljXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
            description = "The lives of two mob hitmen, a boxer, a gangster...",
            userId = "",
            studio = "Miramax",
            poster = "https://m.media-amazon.com/images/M/MV5BNGNhMDIzZTUtNTBlZi00MTRlLWFjM2ItYzViMjE3YzI5MjljXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "Fight Club",
            year = "1999",
            rating = "8.8",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMmEzNTkxYjQtZTc0MC00YTVjLTg5ZTEtZWMwOWVlYzY0NWIwXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
            description = "An insomniac office worker and a devil-may-care...",
            userId = "",
            studio = "20th Century Fox",
            poster = "https://m.media-amazon.com/images/M/MV5BMmEzNTkxYjQtZTc0MC00YTVjLTg5ZTEtZWMwOWVlYzY0NWIwXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "Inception",
            year = "2010",
            rating = "8.8",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_.jpg",
            description = "A thief who steals corporate secrets...",
            userId = "",
            studio = "Warner Bros.",
            poster = "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "The Matrix",
            year = "1999",
            rating = "8.7",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg",
            description = "A computer hacker learns from mysterious rebels...",
            userId = "",
            studio = "Warner Bros.",
            poster = "https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "Goodfellas",
            year = "1990",
            rating = "8.7",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BY2NkZjEzMDgtN2RjYy00YzM1LWI4ZmQtMjIwYjFjNmI3ZGEwXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg",
            description = "The story of Henry Hill and his life in the mob...",
            userId = "",
            studio = "Warner Bros.",
            poster = "https://m.media-amazon.com/images/M/MV5BY2NkZjEzMDgtN2RjYy00YzM1LWI4ZmQtMjIwYjFjNmI3ZGEwXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "The Silence of the Lambs",
            year = "1991",
            rating = "8.6",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BNjNhZTk0ZmEtNjJhMi00YzFlLWE1MmEtYzM1M2ZmMGMwMTU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg",
            description = "A young F.B.I. cadet must receive the help of an incarcerated...",
            userId = "",
            studio = "Orion Pictures",
            poster = "https://m.media-amazon.com/images/M/MV5BNjNhZTk0ZmEtNjJhMi00YzFlLWE1MmEtYzM1M2ZmMGMwMTU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "Interstellar",
            year = "2014",
            rating = "8.6",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
            description = "A team of explorers travel through a wormhole in space...",
            userId = "",
            studio = "Paramount Pictures",
            poster = "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "The Green Mile",
            year = "1999",
            rating = "8.6",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMTUxMzQyNjA5MF5BMl5BanBnXkFtZTYwOTU2NTY3._V1_.jpg",
            description = "The lives of guards on Death Row are affected by one of their charges...",
            userId = "",
            studio = "Warner Bros.",
            poster = "https://m.media-amazon.com/images/M/MV5BMTUxMzQyNjA5MF5BMl5BanBnXkFtZTYwOTU2NTY3._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "Gladiator",
            year = "2000",
            rating = "8.5",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg",
            description = "A former Roman General sets out to exact vengeance...",
            userId = "",
            studio = "DreamWorks",
            poster = "https://m.media-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "The Departed",
            year = "2006",
            rating = "8.5",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMTI1MTY2OTIxNV5BMl5BanBnXkFtZTYwNjQ4NjY3._V1_.jpg",
            description = "An undercover cop and a mole in the police attempt...",
            userId = "",
            studio = "Warner Bros.",
            poster = "https://m.media-amazon.com/images/M/MV5BMTI1MTY2OTIxNV5BMl5BanBnXkFtZTYwNjQ4NjY3._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "The Prestige",
            year = "2006",
            rating = "8.5",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMjA4NDI0MTIxNF5BMl5BanBnXkFtZTYwNTM0MzY2._V1_.jpg",
            description = "Two stage magicians engage in competitive one-upmanship...",
            userId = "",
            studio = "Touchstone Pictures",
            poster = "https://m.media-amazon.com/images/M/MV5BMjA4NDI0MTIxNF5BMl5BanBnXkFtZTYwNTM0MzY2._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "The Lion King",
            year = "1994",
            rating = "8.5",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BYTYxNGMyZTYtMjE3MS00MzNjLWFjNmYtMDk3N2FmM2JiM2M1XkEyXkFqcGdeQXVyNjY5NDU4NzI@._V1_.jpg",
            description = "Lion cub and future king Simba searches for his identity...",
            userId = "",
            studio = "Walt Disney Pictures",
            poster = "https://m.media-amazon.com/images/M/MV5BYTYxNGMyZTYtMjE3MS00MzNjLWFjNmYtMDk3N2FmM2JiM2M1XkEyXkFqcGdeQXVyNjY5NDU4NzI@._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "Avengers: Infinity War",
            year = "2018",
            rating = "8.4",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_.jpg",
            description = "The Avengers and their allies must be willing to sacrifice all...",
            userId = "",
            studio = "Marvel Studios",
            poster = "https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "Whiplash",
            year = "2014",
            rating = "8.5",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
            description = "A promising young drummer enrolls at a cut-throat music conservatory...",
            userId = "",
            studio = "Sony Pictures Classics",
            poster = "https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "The Social Network",
            year = "2010",
            rating = "7.7",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BOGUyZDUxZjEtMmIzMC00MzlmLTg4MGItZWJmMzBhZjE0Mjc1XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
            description = "Harvard student Mark Zuckerberg creates the social networking site...",
            userId = "",
            studio = "Columbia Pictures",
            poster = "https://m.media-amazon.com/images/M/MV5BOGUyZDUxZjEtMmIzMC00MzlmLTg4MGItZWJmMzBhZjE0Mjc1XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "Parasite",
            year = "2019",
            rating = "8.5",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BYWZjMjk3ZTItODQ2ZC00NTY5LWE0ZDYtZTI3MjcwN2Q5NTVkXkEyXkFqcGdeQXVyODk4OTc3MTY@._V1_.jpg",
            description = "Greed and class discrimination threaten the newly formed symbiotic relationship...",
            userId = "",
            studio = "CJ Entertainment",
            poster = "https://m.media-amazon.com/images/M/MV5BYWZjMjk3ZTItODQ2ZC00NTY5LWE0ZDYtZTI3MjcwN2Q5NTVkXkEyXkFqcGdeQXVyODk4OTc3MTY@._V1_.jpg"
        ),
        Movie(
            id = "",
            title = "Joker",
            year = "2019",
            rating = "8.4",
            posterUrl = "https://m.media-amazon.com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg",
            description = "A mentally troubled stand-up comedian embarks on a downward spiral...",
            userId = "",
            studio = "Warner Bros.",
            poster = "https://m.media-amazon.com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg"
        )
    )

    fun loadMovies() {
        _isLoading.value = true
        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("users").document(currentUser.uid)
                .collection("movies")
                .get()
                .addOnSuccessListener { result ->
                    if (result.isEmpty) {
                        // No movies found, load samples
                        loadSampleData()
                    } else {
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

    private fun loadSampleData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // First delete any existing movies (clean slate)
            db.collection("users").document(currentUser.uid)
                .collection("movies")
                .get()
                .addOnSuccessListener { result ->
                    val batch = db.batch()
                    for (document in result) {
                        batch.delete(document.reference)
                    }
                    batch.commit()
                        .addOnSuccessListener {
                            // Now add all sample movies
                            sampleMovies.forEach { movie ->
                                addMovie(movie)
                            }
                        }
                        .addOnFailureListener { e ->
                            _error.value = "Failed to clear existing movies: ${e.message}"
                            _isLoading.value = false
                        }
                }
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