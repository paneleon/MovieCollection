package com.example.moviecollection.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviecollection.R;
import com.example.moviecollection.adapters.MovieListAdapter;
import com.example.moviecollection.model.Movie;
import com.example.moviecollection.model.MovieDao;
import com.example.moviecollection.viewmodel.MovieViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    MovieViewModel movieViewModel;
    ArrayList<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        RecyclerView moviesRecyclerView = findViewById(R.id.movie_list);

        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
//        Movie m = new Movie(1, "Test Movie", "First test movie overview");
//        Movie m2 = new Movie(2, "Test Movie2", "Second test movie overview");
//        movieViewModel.addMovie(m);
//        movieViewModel.addMovie(m2);

//        movieList = new ArrayList<Movie>(Arrays.asList(
//                new Movie("Movie 1"),
//                new Movie("Movie 2"),
//                new Movie("Movie 3")
//        ));

//        movieList = movieViewModel.getMovies();

//        for (Movie movie: movieList) {
//            System.out.println(movie);
//        }

        MovieDao.dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Movie value = child.getValue(Movie.class);
                    movieList.add(value);
                }

//                movieList = movieViewModel.getArrayOfMovies(dataSnapshot);

                MovieListAdapter adapter = new MovieListAdapter(movieList);
                adapter.setClickListener(new MovieListAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        System.out.println(movieList.get(position));
                    }
                });
                moviesRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Movie", "Failed to read value.", error.toException());
            }
        });

//        MovieListAdapter adapter = new MovieListAdapter(movieList);
//        adapter.setClickListener(new MovieListAdapter.ItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                System.out.println(movieList.get(position));
//            }
//        });
//        moviesRecyclerView.setAdapter(adapter);
//
//        for (Movie movie: movieList) {
//            System.out.println("----- movie " + movie);
//        }
    }
}