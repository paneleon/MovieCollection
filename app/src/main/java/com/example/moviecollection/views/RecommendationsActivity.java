package com.example.moviecollection.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.AnimationUtils;
//import androidx.compose.animation.core.Animation;
import android.view.animation.Animation;

import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviecollection.R;
import com.example.moviecollection.adapters.MovieListAdapter;
import com.example.moviecollection.model.Movie;
import com.example.moviecollection.viewmodel.MovieViewModel;
import com.google.firebase.database.annotations.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RecommendationsActivity extends AppCompatActivity {

    private ArrayList<Movie> movieList = new ArrayList<>();
    RecyclerView moviesRecyclerView;
    MovieViewModel movieViewModel;
    ImageView emptyResultImage;
    Animation animation;

    int currentPagePopular = 1;
    int totalPagesPopular = currentPagePopular;

    int currentPageTop = 1;
    int totalPagesTop = currentPageTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        moviesRecyclerView = findViewById(R.id.recommendations_movie_list);

        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Movie> movieList = new ArrayList<>();

        MovieListAdapter adapter = new MovieListAdapter(movieList, MovieListAdapter.ListType.RECOMMENDATIONS);

        moviesRecyclerView.setAdapter(adapter);

        Button buttonForLatest = findViewById(R.id.button_latest_movies);
        Button buttonForTop = findViewById(R.id.button_top_movies);

        buttonForLatest.setOnClickListener(v -> getPopularMovies());
//
        buttonForTop.setOnClickListener(v -> getTopMovies());

        emptyResultImage = findViewById(R.id.empty_result);
        animation = AnimationUtils.loadAnimation(this, R.anim.rotate_tumbleweed);
        emptyResultImage.startAnimation(animation);
    }

    private void getPopularMovies() {
        movieList.clear();
        Pair<MutableLiveData<ArrayList<Movie>>, Integer> response = movieViewModel.getPopularMovies(currentPagePopular);
        totalPagesPopular = Math.min(response.second, 500); // the max is 500 according to docs
        System.out.println("TOTAL POPULAR " + response.second);

        currentPagePopular = ThreadLocalRandom.current().nextInt(1, totalPagesPopular + 1);

//        movieViewModel.getPopularMovies(currentPagePopular).first.observe();
        response.first.observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Movie> movies) {
                movieList = movies;
                if (movieList.size() == 0){
                    emptyResultImage.setVisibility(View.VISIBLE);
                    emptyResultImage.startAnimation(animation);
                } else {
                    emptyResultImage.setVisibility(View.INVISIBLE);
                    moviesRecyclerView.setAdapter(new MovieListAdapter(movieList, MovieListAdapter.ListType.RECOMMENDATIONS, movieViewModel));
                }
            }
        });
    }



    private void getTopMovies() {
        movieList.clear();

        Pair<MutableLiveData<ArrayList<Movie>>, Integer> response = movieViewModel.getTopMovies(currentPageTop);
        totalPagesTop = Math.min(response.second, 500); // the max is 500 according to docs
        System.out.println("TOTAL POPULAR " + response.second);

        currentPageTop = ThreadLocalRandom.current().nextInt(1, totalPagesTop + 1);

//        movieViewModel.getPopularMovies(currentPagePopular).first.observe();
        response.first.observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                totalPagesTop = Math.min(response.second, 500); // the max is 500 according to docs
                System.out.println("TOTAL TOP " + totalPagesTop);

                currentPageTop = ThreadLocalRandom.current().nextInt(1, totalPagesTop + 1);

                movieList = movies;
                if (movieList.size() == 0){
                    emptyResultImage.setVisibility(View.VISIBLE);
                    emptyResultImage.startAnimation(animation);
                } else {
                    emptyResultImage.setVisibility(View.INVISIBLE);
                    moviesRecyclerView.setAdapter(new MovieListAdapter(movieList, MovieListAdapter.ListType.RECOMMENDATIONS, movieViewModel));
                }
            }
        });
    }
}







