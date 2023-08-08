package com.example.moviebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.moviebooking.retrofit.APIUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {


    ImageView thumbnailImageView, MovieImg;
    TextView MovieName, MovieDescription, dateTV, durationnTV,  rateTV, directorTV, actorTV, genreTV;
    Button Booking_btn;

//   ArrayList<Movie> movies ;
    String movieId, movieName, moviePoster, movieDescription, movieTrailer, movieDate, movieDuration, movieRate, movieDirector, movieActor, movieGenre;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Intent intent = getIntent();
//        movies = intent.getParcelableArrayListExtra("mangMovie");
        movieId = intent.getStringExtra("movieId");
        movieName = intent.getStringExtra("movieName");
        movieDescription = intent.getStringExtra("movieDescription");
        moviePoster = intent.getStringExtra("moviePoster");
        movieTrailer = intent.getStringExtra("movieTrailer");
        movieDate = intent.getStringExtra("movieRelease_date");
        movieDuration = intent.getStringExtra("movieDuration");
        movieRate = intent.getStringExtra("movieRating");
        movieDirector = intent.getStringExtra("movieDirector");
        movieActor = intent.getStringExtra("movieActor");
        movieGenre = intent.getStringExtra("movieGenre");
        thumbnailImageView = findViewById(R.id.thumbnailImageView);
//        MovieImg = findViewById(R.id.MovieImg);
        MovieName = findViewById(R.id.MovieName);
        MovieDescription = findViewById(R.id.MovieDescription);
        dateTV = findViewById(R.id.dateTV);
        durationnTV = findViewById(R.id.durationTV);
        rateTV = findViewById(R.id.ratingTV);
        directorTV =findViewById(R.id.directorTV);
        actorTV = findViewById(R.id.actorTV);
        genreTV = findViewById(R.id.genreTV);
        Booking_btn = findViewById(R.id.Book_btn);
        Log.d("BBB", moviePoster);
        String videoId = movieTrailer;
        String thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/mqdefault.jpg";

//        Picasso.get().load(thumbnailUrl).into(thumbnailImageView);
        thumbnailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoId));

                // Check if the YouTube app is installed, otherwise open in a browser
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    // YouTube app is not installed, open in a browser
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoId));
                    startActivity(intent);
                }
            }
        });
//        Picasso.get().load(APIUtils.Base_url + "poster/" + moviePoster).into(MovieImg);
        Picasso.get().load(APIUtils.Base_url + "poster/" + moviePoster).into(thumbnailImageView);
        MovieName.setText(movieName);
        MovieDescription.setText(movieDescription);
        dateTV.setText(movieDate);
        durationnTV.setText(movieDuration);
        rateTV.setText(movieRate);
        directorTV.setText(movieDirector);
        actorTV.setText(movieActor);
        genreTV.setText(movieGenre);
        Booking_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MovieActivity.this, ShowActivity.class);
                intent1.putExtra("movieName", movieName);
                intent1.putExtra("movieId", movieId);
                startActivity(intent1);
            }
        });
    }
}