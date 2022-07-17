package com.example.moviecollection.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.moviecollection.R;
import com.example.moviecollection.helpers.MovieListAdapter;
import com.example.moviecollection.model.Movie;

import java.util.ArrayList;
import java.util.Arrays;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        RecyclerView moviesRecyclerView = findViewById(R.id.movie_list);

        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Movie> movieList = new ArrayList<Movie>(Arrays.asList(
                new Movie("Movie 1"),
                new Movie("Movie 2"),
                new Movie("Movie 3")
        ));

        MovieListAdapter adapter = new MovieListAdapter(movieList);

        adapter.setClickListener(new MovieListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println(movieList.get(position));
            }
        });
        moviesRecyclerView.setAdapter(adapter);
    }
}