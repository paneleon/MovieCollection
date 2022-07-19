package com.example.moviecollection.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.moviecollection.R;
import com.example.moviecollection.adapters.MovieListAdapter;
import com.example.moviecollection.model.Movie;
import com.example.moviecollection.model.MovieDao;
import com.example.moviecollection.viewmodel.MovieViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WatchedMoviesActivity extends AppCompatActivity {

    MovieViewModel movieViewModel;
    MovieListAdapter adapter;
    ArrayList<Movie> movies = new ArrayList<>();
    ImageView emptyResultImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watched_movies);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        RecyclerView listRecyclerView = findViewById(R.id.watched_movie_list);
        emptyResultImage = findViewById(R.id.empty_result);

        Query watchedMoviesQuery = MovieDao.dbRef.orderByChild("seen").equalTo(true);
        watchedMoviesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                movies.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Movie movie = snapshot.getValue(Movie.class);
                    movie.setKey(snapshot.getKey());
                    movies.add(movie);
                }

                if (movies.size() == 0){
                    emptyResultImage.setVisibility(View.VISIBLE);
                } else {
                    emptyResultImage.setVisibility(View.INVISIBLE);

                    adapter = new MovieListAdapter(movies, MovieListAdapter.ListType.WATCHED, movieViewModel);
                    listRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Watched Movies", "Failed to read values.", error.toException());
            }
        });
    }
}