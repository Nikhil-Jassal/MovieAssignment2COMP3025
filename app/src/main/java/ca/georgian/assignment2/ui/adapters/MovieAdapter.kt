package ca.georgian.assignment2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.georgian.assignment2.data.Movie
import ca.georgian.assignment2.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val onItemClick: (Movie) -> Unit,
    private val onEditClick: (Movie) -> Unit,
    private val onDeleteClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movies = emptyList<Movie>()

    fun submitList(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.movieStudio.text = movie.studio
            binding.movieRating.text = "Rating: ${movie.rating}"
            binding.movieYear.text = movie.year

            Picasso.get()
                .load(movie.poster)
                //doubt for this r?
                .placeholder(R.drawable.ic_movie_placeholder)
                .into(binding.moviePoster)

            binding.root.setOnClickListener { onItemClick(movie) }
            binding.btnEdit.setOnClickListener { onEditClick(movie) }
            binding.btnDelete.setOnClickListener { onDeleteClick(movie) }
        }
    }
}