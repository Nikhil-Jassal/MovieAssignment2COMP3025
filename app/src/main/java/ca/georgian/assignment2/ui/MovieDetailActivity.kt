package ca.georgian.assignment2.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ca.georgian.assignment2.data.Movie
import ca.georgian.assignment2.databinding.ActivityMovieDetailBinding
import ca.georgian.assignment2.viewmodel.MovieViewModel
import com.squareup.picasso.Picasso

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private val movieViewModel: MovieViewModel by viewModels()
    private var mode: String? = null
    private var documentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mode = intent.getStringExtra("mode")
        documentId = intent.getStringExtra("documentId")

        if (mode == "edit") {
            binding.etTitle.setText(intent.getStringExtra("title"))
            binding.etStudio.setText(intent.getStringExtra("studio"))
            binding.etRating.setText(intent.getStringExtra("rating"))
            binding.etYear.setText(intent.getStringExtra("year"))
            binding.etPoster.setText(intent.getStringExtra("poster"))
            binding.etDescription.setText(intent.getStringExtra("description"))

            Picasso.get().load(intent.getStringExtra("poster")).into(binding.ivPoster)
            binding.btnSave.text = "Update"
        } else {
            binding.btnSave.text = "Add"
        }

        binding.btnSave.setOnClickListener {
            saveMovie()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnDelete.setOnClickListener {
            documentId?.let { id ->
                movieViewModel.deleteMovie(id)
                finish()
            } ?: run {
                Toast.makeText(this, "Cannot delete new movie", Toast.LENGTH_SHORT).show()
            }
        }

        // Show delete button only in edit mode
        binding.btnDelete.visibility = if (mode == "edit") android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun saveMovie() {
        val title = binding.etTitle.text.toString().trim()
        val studio = binding.etStudio.text.toString().trim()
        val rating = binding.etRating.text.toString().trim()
        val year = binding.etYear.text.toString().trim()
        val poster = binding.etPoster.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()

        if (title.isEmpty() || studio.isEmpty() || rating.isEmpty() || year.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val movie = Movie(
            id = documentId ?: "",
            title = title,
            studio = studio,
            rating = rating,
            year = year,
            posterUrl = poster,
            description = description,
            userId = "", // Set this to current user's ID if needed
            poster = poster
        )

        if (mode == "edit" && documentId != null) {
            movieViewModel.updateMovie(documentId!!, movie)
        } else {
            movieViewModel.addMovie(movie)
        }

        finish()
    }
}