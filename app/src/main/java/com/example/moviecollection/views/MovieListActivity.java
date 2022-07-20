package com.example.moviecollection.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    MovieViewModel movieViewModel;
    ArrayList<Movie> movieList = new ArrayList<>();
    MovieListAdapter adapter;
    ImageView emptyResultImage;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        RecyclerView moviesRecyclerView = findViewById(R.id.movie_list);

        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        emptyResultImage = findViewById(R.id.empty_result);
        animation = AnimationUtils.loadAnimation(this, R.anim.rotate_tumbleweed);
        emptyResultImage.startAnimation(animation);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        movieViewModel.getMoviesToWatch().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Movie> movies) {
                if (movies.size() == 0){
                    emptyResultImage.setVisibility(View.VISIBLE);
                    emptyResultImage.startAnimation(animation);
                } else {
                    emptyResultImage.setVisibility(View.INVISIBLE);
                    adapter = new MovieListAdapter(movies, MovieListAdapter.ListType.SAVED, movieViewModel);
                    moviesRecyclerView.setAdapter(adapter);
                }
            }
        });
    }
}