package ca.georgian.assignment2.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ca.georgian.assignment2.R
import ca.georgian.assignment2.data.Movie
import ca.georgian.assignment2.viewmodels.MovieViewModel

class AddEditMovieActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityAddEditBinding
    private val movieViewModel: MovieViewModel by viewModels()
    private var currentMovie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mode = intent.getStringExtra("mode")
        currentMovie = intent.getParcelableExtra("movie")

        if (mode == "edit" && currentMovie != null) {
            binding.tvTitle.text = "Edit Movie"
            binding.btnSubmit.text = "Update"
            populateFields(currentMovie!!)
        } else {
            binding.tvTitle.text = "Add Movie"
            binding.btnSubmit.text = "Save"
        }

        binding.btnSubmit.setOnClickListener {
            if (validateInputs()) {
                val movie = createMovieFromInputs()
                if (mode == "edit") {
                    movieViewModel.updateMovie(movie)
                } else {
                    movieViewModel.addMovie(movie)
                }
                finish()
            }
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun populateFields(movie: Movie) {
        // Populate all fields with movie data
    }

    private fun validateInputs(): Boolean {
        // Validation logic
        return true
    }

    private fun createMovieFromInputs(): Movie {
        // Create movie object from inputs
        return Movie(...)
    }
}