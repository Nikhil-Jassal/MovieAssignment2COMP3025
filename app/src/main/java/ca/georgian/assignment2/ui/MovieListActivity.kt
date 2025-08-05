package ca.georgian.assignment2.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ca.georgian.assignment2.databinding.ActivityMovieListBinding
import ca.georgian.assignment2.ui.adapters.MovieAdapter
import ca.georgian.assignment2.viewmodels.MovieViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MovieListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieListBinding
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check authentication
        if (Firebase.auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setupRecyclerView()
        observeMovies()

        binding.fabAddMovie.setOnClickListener {
            startActivity(Intent(this, AddEditMovieActivity::class.java).apply {
                putExtra("mode", "add")
            })
        }
    }

    private fun setupRecyclerView() {
        adapter = MovieAdapter(
            onItemClick = { movie ->
                startActivity(Intent(this, MovieDetailActivity::class.java).apply {
                    putExtra("movie", movie)
                }
            },
            onEditClick = { movie ->
                startActivity(Intent(this, AddEditMovieActivity::class.java).apply {
                    putExtra("mode", "edit")
                    putExtra("movie", movie)
                }
            },
            onDeleteClick = { movie ->
                viewModel.deleteMovie(movie)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MovieListActivity)
            adapter = this@MovieListActivity.adapter
        }
    }

    private fun observeMovies() {
        viewModel.movies.observe(this) { movies ->
            adapter.submitList(movies)
        }
    }
}