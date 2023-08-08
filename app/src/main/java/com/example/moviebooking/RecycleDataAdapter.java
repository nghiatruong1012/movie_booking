package com.example.moviebooking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviebooking.retrofit.APIUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecycleDataAdapter extends RecyclerView.Adapter<RecycleDataAdapter.ViewHolder> {

        private ArrayList<Movie> movies;
        private Context context;

    public RecycleDataAdapter(ArrayList<Movie> movies, FragmentActivity context) {
        this.movies = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleDataAdapter.ViewHolder holder, int position) {

        holder.movieName.setText(movies.get(position).getName());
//        holder.movieDescription.setText(movies.get(position).getDescription());
        Picasso.get().load(APIUtils.Base_url + "poster/" + movies.get(position).getPoster()).into(holder.moviePoster);
        holder.itemLayout.setOnClickListener(view -> {
            int currentPosition = holder.getAdapterPosition();
            Intent intent = new Intent(this.context, MovieActivity.class);
            intent.putExtra("movieId", movies.get(position).getId());
            intent.putExtra("movieName", movies.get(position).getName());
            intent.putExtra("moviePoster", movies.get(position).getPoster());
            intent.putExtra("movieDescription", movies.get(position).getDescription());
            intent.putExtra("movieTrailer", movies.get(position).getTrailer());
            intent.putExtra("movieRelease_date", movies.get(position).getReleaseDate());
            intent.putExtra("movieDuration", movies.get(position).getDuration());
            intent.putExtra("movieRating", movies.get(position).getRating());
            intent.putExtra("movieDirector", movies.get(position).getDirector());
            intent.putExtra("movieActor", movies.get(position).getActor());
            intent.putExtra("movieGenre", movies.get(position).getGenre());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
//        return movies.size();
        if(movies!=null){
            return movies.size();
        }else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieName;//, movieDescription;
        ImageView moviePoster;
        LinearLayout itemLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movieName);
//            movieDescription = itemView.findViewById(R.id.movieDescription);
            moviePoster = itemView.findViewById(R.id.movie_poster);
            itemLayout = itemView.findViewById(R.id.itemLayout);
        }
    }
}
