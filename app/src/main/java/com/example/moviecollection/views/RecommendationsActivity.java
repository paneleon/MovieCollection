package com.example.moviecollection.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class RecommendationsActivity extends AppCompatActivity {

    private ArrayList<Movie> movieList = new ArrayList<>();
    RecyclerView moviesRecyclerView;
    public static final String API_KEY = "";
    MovieViewModel movieViewModel;
    ImageView emptyResultImage;

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

        buttonForTop.setOnClickListener(v -> getTopMovies());

        emptyResultImage = findViewById(R.id.empty_result);

    }

    private void getPopularMovies() {
        movieList.clear();
        String endpoint = String.format("https://api.themoviedb.org/3/movie/popular?api_key=%s&language=en-US&page=%d", API_KEY, currentPagePopular);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, endpoint,null, new Response.Listener<JSONArray>(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, endpoint,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    Log.d("search movies", "total pages ==== " + response.getString("total_pages"));

                    totalPagesPopular = Math.min(Integer.parseInt(response.getString("total_pages")), 500); // the max is 500 according to docs
                    currentPagePopular = ThreadLocalRandom.current().nextInt(1, totalPagesPopular + 1);

                    if (results.length() == 0){
                        emptyResultImage.setVisibility(View.VISIBLE);
                    } else {
                        emptyResultImage.setVisibility(View.INVISIBLE);
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject jsonObject = results.getJSONObject(i);
                            Log.d("my-api", "==== " + jsonObject.getString("title"));
                            movieList.add(new Movie(Integer.parseInt(jsonObject.getString("id")),
                                            jsonObject.getString("title"),
                                            jsonObject.getString("overview"),
                                            (jsonObject.has("release_date") ? jsonObject.getString("release_date") : ""),
                                            Double.parseDouble(jsonObject.getString("vote_average"))
                                    )
                            );
                        }
                        moviesRecyclerView.setAdapter(new MovieListAdapter(movieList, MovieListAdapter.ListType.RECOMMENDATIONS, movieViewModel));
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("my-api",error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }



    private void getTopMovies(){
        movieList.clear();
        String endpoint = String.format("https://api.themoviedb.org/3/movie/top_rated?api_key=%s&language=en-US&page=%d", API_KEY, currentPageTop);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, endpoint,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    Log.d("search movies", "total ==== " + response.getString("total_results"));
                    totalPagesTop = Math.min(Integer.parseInt(response.getString("total_pages")), 500); // the max is 500 according to docs
                    currentPageTop = ThreadLocalRandom.current().nextInt(1, totalPagesTop + 1);

                    if (results.length() == 0){
                        emptyResultImage.setVisibility(View.VISIBLE);
                    } else {
                        emptyResultImage.setVisibility(View.INVISIBLE);
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject jsonObject = results.getJSONObject(i);
                            Log.d("my-api", "==== " + jsonObject.getString("title"));
                            movieList.add(new Movie(Integer.parseInt(jsonObject.getString("id")),
                                            jsonObject.getString("title"),
                                            jsonObject.getString("overview"),
                                            (jsonObject.has("release_date") ? jsonObject.getString("release_date") : ""),
                                            Double.parseDouble(jsonObject.getString("vote_average"))
                                    )
                            );
                        }
                        moviesRecyclerView.setAdapter(new MovieListAdapter(movieList, MovieListAdapter.ListType.RECOMMENDATIONS, movieViewModel));
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("my-api",error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

//    public ArrayList<Movie> getMoviesFromAPI(){
//
//    }
}







