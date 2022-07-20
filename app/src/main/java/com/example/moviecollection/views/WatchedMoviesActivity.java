package com.example.moviecollection.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.moviecollection.R;
import com.example.moviecollection.adapters.MovieListAdapter;
import com.example.moviecollection.model.Movie;
import com.example.moviecollection.viewmodel.MovieViewModel;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class WatchedMoviesActivity extends AppCompatActivity {

    MovieViewModel movieViewModel;
    MovieListAdapter adapter;
    ArrayList<Movie> movies = new ArrayList<>();
    ImageView emptyResultImage;
    Animation animation;
    RecyclerView moviesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watched_movies);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        moviesRecyclerView = findViewById(R.id.watched_movie_list);
        emptyResultImage = findViewById(R.id.empty_result);

        animation = AnimationUtils.loadAnimation(this, R.anim.rotate_tumbleweed);
        emptyResultImage.startAnimation(animation);

        movieViewModel.getWatchedMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Movie> movies) {
                if (movies.size() == 0){
                    emptyResultImage.setVisibility(View.VISIBLE);
                    emptyResultImage.startAnimation(animation);
                } else {
                    emptyResultImage.setVisibility(View.INVISIBLE);
                    adapter = new MovieListAdapter(movies, MovieListAdapter.ListType.WATCHED, movieViewModel);
                    moviesRecyclerView.setAdapter(adapter);
                }
            }
        });
    }
}