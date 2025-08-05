package ca.georgian.assignment2.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.georgian.assignment2.MyViewHolder
import ca.georgian.assignment2.R
import ca.georgian.assignment2.data.Movie
import com.squareup.picasso.Picasso

class MyAdapter(
    private val context: Context,
    private var movies: List<Pair<Movie, String>>,
    private val onItemClick: (Movie, String) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {

    fun updateMovies(newMovies: List<Pair<Movie, String>>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (movie, documentId) = movies[position]

        holder.title.text = movie.title
        holder.studio.text = "Studio: ${movie.studio}"
        holder.rating.text = "Rating: ${movie.rating}"
        holder.year.text = "Year: ${movie.year}"

        Picasso.get()
            .load(movie.poster)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(holder.poster)

        holder.itemView.setOnClickListener {
            onItemClick(movie, documentId)
        }
    }

    override fun getItemCount() = movies.size
}