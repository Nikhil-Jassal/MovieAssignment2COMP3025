package ca.georgian.assignment2

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.movieTitle)
    val studio: TextView = itemView.findViewById(R.id.movieStudio)
    val rating: TextView = itemView.findViewById(R.id.movieRating)
    val year: TextView = itemView.findViewById(R.id.movieYear)
    val poster: ImageView = itemView.findViewById(R.id.moviePoster)
}