package com.example.moviecollection.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

        MovieDao.dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                movieList.clear();

                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Movie value = child.getValue(Movie.class);
                    value.setKey(child.getKey());
                    movieList.add(value);
                }

                if (movieList.size() == 0){
                    emptyResultImage.setVisibility(View.VISIBLE);
                    emptyResultImage.startAnimation(animation);
                } else {
                    emptyResultImage.setVisibility(View.INVISIBLE);

                    adapter = new MovieListAdapter(movieList, MovieListAdapter.ListType.SAVED, movieViewModel);
                    moviesRecyclerView.setAdapter(adapter);
                }
//                movieList = movieViewModel.getArrayOfMovies(dataSnapshot);
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