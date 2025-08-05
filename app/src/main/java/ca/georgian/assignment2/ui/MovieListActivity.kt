package ca.georgian.assignment2.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ca.georgian.assignment2.auth.LoginActivity
import ca.georgian.assignment2.data.Movie
import ca.georgian.assignment2.databinding.ActivityMovieListBinding
import ca.georgian.assignment2.ui.adapters.MovieAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MovieListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieListBinding
    private lateinit var adapter: MovieAdapter
    private lateinit var db: FirebaseFirestore

    companion object {
        private const val TAG = "MovieListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firestore
        db = Firebase.firestore

        // Check authentication
        if (Firebase.auth.currentUser == null) {
            Log.d(TAG, "User not authenticated, redirecting to login")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setupRecyclerView()
        loadMovies()

        binding.fabAddMovie.setOnClickListener {
            Log.d(TAG, "Add movie button clicked")
            startActivity(Intent(this, AddEditMovieActivity::class.java).apply {
                putExtra("mode", "add")
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = MovieAdapter(
            onItemClick = { movie ->
                Log.d(TAG, "Movie clicked: ${movie.title}")
                startActivity(Intent(this, MovieDetailActivity::class.java).apply {
                    putExtra("movie", movie)
                }
            },
            onEditClick = { movie ->
                Log.d(TAG, "Edit movie clicked: ${movie.title}")
                startActivity(Intent(this, AddEditMovieActivity::class.java).apply {
                    putExtra("mode", "edit")
                    putExtra("movie", movie)
                }
            },
            onDeleteClick = { movie ->
                Log.d(TAG, "Delete movie clicked: ${movie.title}")
                deleteMovie(movie)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MovieListActivity)
            adapter = this@MovieListActivity.adapter
        }
    }

    private fun loadMovies() {
        val currentUser = Firebase.auth.currentUser
        currentUser?.let { user ->
            db.collection("movies")
                .whereEqualTo("userId", user.uid)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.w(TAG, "Listen failed.", error)
                        return@addSnapshotListener
                    }

                    val movies = mutableListOf<Movie>()
                    snapshot?.documents?.forEach { doc ->
                        doc.toObject(Movie::class.java)?.let { movie ->
                            movies.add(movie.copy(id = doc.id))
                        }
                    }
                    Log.d(TAG, "Loaded ${movies.size} movies")
                    adapter.submitList(movies)
                }
        }
    }

    private fun deleteMovie(movie: Movie) {
        if (movie.id.isNotEmpty()) {
            db.collection("movies").document(movie.id)
                .delete()
                .addOnSuccessListener {
                    Log.d(TAG, "Movie successfully deleted")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error deleting movie", e)
                }
        }
    }
}