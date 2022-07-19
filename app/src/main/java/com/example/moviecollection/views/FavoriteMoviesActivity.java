package com.example.moviecollection.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class FavoriteMoviesActivity extends AppCompatActivity {

    MovieViewModel movieViewModel;
    MovieListAdapter adapter;
    ArrayList<Movie> movies = new ArrayList<Movie>();
    ImageView emptyResultImage;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);

        RecyclerView moviesRecyclerView = findViewById(R.id.favorite_movie_list);
        emptyResultImage = findViewById(R.id.empty_result);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
//        adapter = new MovieListAdapter(movies, MovieListAdapter.ListType.FAVORITES, movieViewModel);

        animation = AnimationUtils.loadAnimation(this, R.anim.rotate_tumbleweed);
        emptyResultImage.startAnimation(animation);

        //        MovieDao.dbRef.orderByChild("favorite").equalTo(true).addValueEventListener(new ValueEventListener() {
        Query favoriteMovieQuery = MovieDao.dbRef.orderByChild("favorite").equalTo(true);
        favoriteMovieQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                movies.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    System.out.println("-------" + snapshot.toString());
                    Movie movie = snapshot.getValue(Movie.class);
                    movie.setKey(snapshot.getKey());
                    movies.add(movie);
                }

                if (movies.size() == 0){
                    emptyResultImage.setVisibility(View.VISIBLE);
                    emptyResultImage.startAnimation(animation);
                } else {
                    emptyResultImage.setVisibility(View.INVISIBLE);

                    adapter = new MovieListAdapter(movies, MovieListAdapter.ListType.FAVORITES, movieViewModel);
                    moviesRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Favorite Movies", "Failed to read values.", error.toException());
            }
        });

    }
}