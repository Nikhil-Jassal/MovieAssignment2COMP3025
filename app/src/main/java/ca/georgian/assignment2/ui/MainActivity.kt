package ca.georgian.assignment2.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ca.georgian.assignment2.R
import ca.georgian.assignment2.auth.LoginActivity
import ca.georgian.assignment2.databinding.ActivityMainBinding
import ca.georgian.assignment2.ui.adapters.MyAdapter
import ca.georgian.assignment2.viewmodel.AuthViewModel
import ca.georgian.assignment2.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val movieViewModel: MovieViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()

        binding.fabAddMovie.setOnClickListener {
            val intent = Intent(this, MovieDetailActivity::class.java).apply {
                putExtra("mode", "add")
            }
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        adapter = MyAdapter(this, emptyList()) { movie, documentId ->
            val intent = Intent(this, MovieDetailActivity::class.java).apply {
                putExtra("mode", "edit")
                putExtra("documentId", documentId)
                putExtra("title", movie.title)
                putExtra("studio", movie.studio)
                putExtra("rating", movie.rating)
                putExtra("year", movie.year)
                putExtra("poster", movie.poster)
                putExtra("posterUrl", movie.posterUrl)
                putExtra("description", movie.description)
            }
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        movieViewModel.movies.observe(this) { movies ->
            adapter.updateMovies(movies)
        }

        movieViewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        authViewModel.authState.observe(this) { state ->
            if (state is AuthViewModel.AuthState.Unauthenticated) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> {
                authViewModel.logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        movieViewModel.loadMovies()
    }
}