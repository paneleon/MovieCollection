package com.example.moviecollection.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class MovieSearchActivity extends AppCompatActivity {

    //TODO: pagination


    String searchText;
    ArrayList<Movie> movies = new ArrayList<>();
    RecyclerView moviesRecyclerView;
    MovieViewModel movieViewModel;
    ImageView emptyResultImage;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        ImageButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movies.clear();
                searchText = ((EditText)findViewById(R.id.search_text)).getText().toString();
                findMovie(searchText);
            }
        });

        moviesRecyclerView = findViewById(R.id.movie_search_list);
        emptyResultImage = findViewById(R.id.empty_result);
        animation = AnimationUtils.loadAnimation(this, R.anim.rotate_tumbleweed);
        emptyResultImage.startAnimation(animation);
    }

    private void findMovie(String title){
        movieViewModel.searchMovie(title).observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Movie> movies) {
                if (movies.size() == 0){
                    emptyResultImage.setVisibility(View.VISIBLE);
                    emptyResultImage.startAnimation(animation);
                } else {
                    emptyResultImage.setVisibility(View.INVISIBLE);
                    moviesRecyclerView.setAdapter(new MovieListAdapter(movies, MovieListAdapter.ListType.RECOMMENDATIONS, movieViewModel));
                }
            }
        });
    }
}