package ca.georgian.assignment2.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ca.georgian.assignment2.data.Movie
import ca.georgian.assignment2.databinding.ActivityAddEditBinding
import ca.georgian.assignment2.viewmodels.MovieViewModel

class AddEditMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditBinding
    private val viewModel: MovieViewModel by viewModels()
    private var currentMovie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mode = intent.getStringExtra("mode")
        currentMovie = intent.getParcelableExtra("movie")

        if (mode == "edit" && currentMovie != null) {
            setupEditMode()
        } else {
            setupAddMode()
        }

        setupButtons()
    }

    private fun setupEditMode() {
        binding.tvTitle.text = "Edit Movie"
        binding.btnSubmit.text = "Update"
        currentMovie?.let { movie ->
            binding.etTitle.setText(movie.title)
            binding.etStudio.setText(movie.studio)
            binding.etRating.setText(movie.rating)
            binding.etYear.setText(movie.year)
            binding.etPoster.setText(movie.poster)
            binding.etDescription.setText(movie.description)
        }
    }

    private fun setupAddMode() {
        binding.tvTitle.text = "Add Movie"
        binding.btnSubmit.text = "Save"
    }

    private fun setupButtons() {
        binding.btnSubmit.setOnClickListener {
            if (validateInputs()) {
                val movie = createMovieFromInputs()
                if (binding.btnSubmit.text == "Update") {
                    viewModel.updateMovie(movie)
                } else {
                    viewModel.addMovie(movie)
                }
                finish()
            }
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun validateInputs(): Boolean {
        // Add validation logic
        return true
    }

    private fun createMovieFromInputs(): Movie {
        return currentMovie?.copy(
            title = binding.etTitle.text.toString(),
            studio = binding.etStudio.text.toString(),
            rating = binding.etRating.text.toString(),
            year = binding.etYear.text.toString(),
            poster = binding.etPoster.text.toString(),
            description = binding.etDescription.text.toString()
        ) ?: Movie(
            title = binding.etTitle.text.toString(),
            studio = binding.etStudio.text.toString(),
            rating = binding.etRating.text.toString(),
            year = binding.etYear.text.toString(),
            poster = binding.etPoster.text.toString(),
            description = binding.etDescription.text.toString()
        )
    }
}