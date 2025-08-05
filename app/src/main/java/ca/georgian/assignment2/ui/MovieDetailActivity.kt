package ca.georgian.assignment2.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_detail)

        val title = intent.getStringExtra("title")
        val studio = intent.getStringExtra("studio")
        val rating = intent.getStringExtra("rating")
        val year = intent.getStringExtra("year")
        val poster = intent.getStringExtra("poster")
        val description = intent.getStringExtra("description")

        findViewById<TextView>(R.id.detailTitle).text = title
        findViewById<TextView>(R.id.detailStudio).text = "Studio: $studio"
        findViewById<TextView>(R.id.detailRating).text = "Rating: $rating"
        findViewById<TextView>(R.id.detailYear).text = "Year: $year"
        findViewById<TextView>(R.id.detailDescription).text = description
        val posterImageView = findViewById<ImageView>(R.id.detailPoster)

        Picasso.get().load(poster).into(posterImageView)

        findViewById<ImageView>(R.id.backIcon).setOnClickListener {
            finish()
        }
    }
}
