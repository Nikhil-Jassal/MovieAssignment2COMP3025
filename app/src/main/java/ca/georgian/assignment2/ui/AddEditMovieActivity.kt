package ca.georgian.assignment2.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ca.georgian.assignment2.data.Movie
import ca.georgian.assignment2.databinding.ActivityAddEditBinding

class AddEditMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditBinding
    private val viewModel: MovieViewModel by viewModels()
    private var currentMovieId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mode = intent.getStringExtra("mode")
        currentMovieId = intent.getStringExtra("id") ?: ""

        if (mode == "edit") {
            setupEditMode()
        } else {
            setupAddMode()
        }

        setupButtons()
    }

    private fun setupEditMode() {
        binding.tvTitle.text = "Edit Movie"
        binding.btnSubmit.text = "Update"

        binding.etTitle.setText(intent.getStringExtra("title"))
        binding.etStudio.setText(intent.getStringExtra("studio"))
        binding.etRating.setText(intent.getStringExtra("rating"))
        binding.etYear.setText(intent.getStringExtra("year"))
        binding.etPoster.setText(intent.getStringExtra("poster"))
        binding.etDescription.setText(intent.getStringExtra("description"))
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
                    viewModel.updateMovie(movie.copy(id = currentMovieId))
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
        var isValid = true

        if (binding.etTitle.text.toString().isEmpty()) {
            binding.etTitle.error = "Title is required"
            isValid = false
        }

        if (binding.etStudio.text.toString().isEmpty()) {
            binding.etStudio.error = "Studio is required"
            isValid = false
        }

        return isValid
    }

    private fun createMovieFromInputs(): Movie {
        return Movie(
            title = binding.etTitle.text.toString(),
            studio = binding.etStudio.text.toString(),
            rating = binding.etRating.text.toString(),
            year = binding.etYear.text.toString(),
            poster = binding.etPoster.text.toString(),
            description = binding.etDescription.text.toString()
        )
    }
}