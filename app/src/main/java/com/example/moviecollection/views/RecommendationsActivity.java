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

public class RecommendationsActivity extends AppCompatActivity {

    private ArrayList<Movie> movieList = new ArrayList<>();
    RecyclerView moviesRecyclerView;
    public static final String API_KEY = "";
    MovieViewModel movieViewModel;
    ImageView emptyResultImage;

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

        buttonForLatest.setOnClickListener(v -> getLatestMovies());

        buttonForTop.setOnClickListener(v -> getTopMovies());

        emptyResultImage = findViewById(R.id.empty_result);

    }

    private void getLatestMovies() {
        String endpoint = String.format("https://api.themoviedb.org/3/movie/popular?api_key=%s&language=en-US&page=1", API_KEY);
//
//        ArrayList<String> result =  new ArrayList<>(0);
//
//        URL url = new URL(endpoint);
//        URLLConnection connection = url.openConnection();
//
//
//        Thread t = new Thread(new Runnable() {
//            public void run() {
//                // Perform Network operations and processing.
//                final MyDataClass result = loadInBackground();
//                // Synchronize with the UI thread to post changes.
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println(result);
//                    }
//                });
//            }
//        });
//        t.start();


        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, endpoint,null, new Response.Listener<JSONArray>(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, endpoint,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");

                    if (results.length() == 0){
                        emptyResultImage.setVisibility(View.VISIBLE);
                    } else {
                        emptyResultImage.setVisibility(View.INVISIBLE);
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject jsonObject = results.getJSONObject(i);

                            Log.d("my-api", "==== " + jsonObject.getString("title"));
                            Log.d("my-api", "==== " + jsonObject.getString("overview"));
                            Log.d("my-api", "==== " + jsonObject.getString("id"));
                            movieList.add(new Movie(Integer.parseInt(jsonObject.getString("id")),
                                            jsonObject.getString("title"),
                                            jsonObject.getString("overview")
                                    )
                            );
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                moviesRecyclerView.setAdapter(new MovieListAdapter(movieList, MovieListAdapter.ListType.RECOMMENDATIONS, movieViewModel));
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

    }
}







