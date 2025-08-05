package ca.georgian.assignment2.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.georgian.assignment2.model.Item
import com.squareup.picasso.Picasso

class MyAdapter(
    private val context: Context,
    private var movies: List<Pair<Item, String>>,
    private val onItemClick: (Item, String) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {

    fun updateMovies(newMovies: List<Pair<Item, String>>) {
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

        holder.title.text = movie.getTitle()
        holder.studio.text = "Studio: ${movie.getStudio()}"
        holder.rating.text = "Rating: ${movie.getRating()}"
        holder.year.text = "Year: ${movie.getYear()}"

        Picasso.get()
            .load(movie.getPoster())
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(holder.poster)

        holder.itemView.setOnClickListener {
            onItemClick(movie, documentId)
        }
    }

    override fun getItemCount() = movies.size
}