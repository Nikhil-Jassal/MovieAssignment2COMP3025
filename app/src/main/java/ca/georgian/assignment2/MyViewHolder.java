package ca.georgian.assignment2;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import  androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder{

    TextView title;
    TextView studio;
    TextView rating;
    TextView year;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.movieTitle);
        studio = itemView.findViewById(R.id.movieStudio);
        rating = itemView.findViewById(R.id.movieRating);
        year = itemView.findViewById(R.id.movieYear);
    }
}
