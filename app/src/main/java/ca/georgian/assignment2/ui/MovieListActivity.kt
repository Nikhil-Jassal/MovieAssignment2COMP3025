package ca.georgian.assignment2.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import ca.georgian.assignment2.R
import ca.georgian.assignment2.ui.adapters.MovieAdapter
import ca.georgian.assignment2.viewmodels.MovieViewModel

class ActivityMovieListBinding
{

}

class MovieListActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMovieListBinding
    private lateinit var movieAdapter: MovieAdapter
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeMovies()

        binding.fabAddMovie.setOnClickListener {
            startActivity(Intent(this, AddEditMovieActivity::class.java).apply {
                putExtra("mode", "add")
            }
        }
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter { movie ->
            // Handle item click
            startActivity(Intent(this, MovieDetailActivity::class.java).apply {
                putExtra("movie", movie)
            }
        }

        binding.recyclerView.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(this@MovieListActivity)
        }
    }

    private fun observeMovies() {
        movieViewModel.movies.observe(this) { movies ->
            movieAdapter.submitList(movies)
        }
    }
}