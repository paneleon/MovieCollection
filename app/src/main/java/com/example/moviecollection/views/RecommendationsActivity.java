package com.example.moviecollection.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviecollection.R;
import com.example.moviecollection.helpers.MovieListAdapter;
import com.example.moviecollection.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class RecommendationsActivity extends AppCompatActivity {

    private ArrayList<Movie> movieList = new ArrayList<>();
    RecyclerView moviesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);



//        https://api.themoviedb.org/3/movie/latest?api_key=<<api_key>>&language=en-US

        // b1c39b43abc3c3c914005b1d3ebaaaf8

        moviesRecyclerView = findViewById(R.id.recommendations_movie_list);

        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Movie> movieList = new ArrayList<Movie>(Arrays.asList(
                new Movie("Movie 4"),
                new Movie("Movie 5"),
                new Movie("Movie 6")
        ));

        MovieListAdapter adapter = new MovieListAdapter(movieList);

        adapter.setClickListener(new MovieListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println(movieList.get(position));
            }
        });
        moviesRecyclerView.setAdapter(adapter);

        Button buttonForLatest = findViewById(R.id.button_latest_movies);
        Button buttonForTop = findViewById(R.id.button_top_movies);

        buttonForLatest.setOnClickListener(v -> getLatestMovies());

        buttonForTop.setOnClickListener(v -> getTopMovies());
    }

    private void getLatestMovies() {
        String endpoint = "https://api.themoviedb.org/3/movie/popular?api_key={api_key}&language=en-US&page=1";
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

                    for (int i = 0; i < results.length(); i++){
                        JSONObject jsonObject = results.getJSONObject(i);

                        Log.d("my-api","==== "+ jsonObject.getString("title"));
                        Log.d("my-api","==== "+ jsonObject.getString("overview"));
                        Log.d("my-api","==== "+ jsonObject.getString("id"));
                        movieList.add(new Movie(Integer.parseInt(jsonObject.getString("id")),
                                jsonObject.getString("title"),
                                jsonObject.getString("overview")
                                )
                        );
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                moviesRecyclerView.setAdapter(new MovieListAdapter(movieList));
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







