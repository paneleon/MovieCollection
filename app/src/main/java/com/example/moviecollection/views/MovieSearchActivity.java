package com.example.moviecollection.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

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

public class MovieSearchActivity extends AppCompatActivity {

    public static final String API_KEY = "";
    String searchText;
    ArrayList<Movie> movies = new ArrayList<>();
    RecyclerView moviesRecyclerView;
    MovieViewModel movieViewModel;

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
                searchMovie(searchText);
            }
        });

        moviesRecyclerView = findViewById(R.id.movie_search_list);
    }

    private void searchMovie(String title){
        
        String formattedTitle = title.trim().replace(' ', '+');
        // Jack+Reacher
        String endpoint = String.format("https://api.themoviedb.org/3/search/movie?api_key=%s&query=%s", API_KEY, formattedTitle);

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

                        Log.d("search movies","total ==== "+ response.getString("total_results"));
                        Log.d("search movies","page ==== "+ response.getString("page"));
                        Log.d("search movies","total pages ==== "+ response.getString("total_pages"));

                        Log.d("search movies","==== "+ jsonObject.getString("id"));
                        movies.add(new Movie(Integer.parseInt(jsonObject.getString("id")),
                                        jsonObject.getString("title"),
                                        jsonObject.getString("overview"),
                                        (jsonObject.has("release_date") ? jsonObject.getString("release_date") : ""),
                                        Double.parseDouble(jsonObject.getString("vote_average"))
                                )
                        );
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                moviesRecyclerView.setAdapter(new MovieListAdapter(movies, MovieListAdapter.ListType.RECOMMENDATIONS, movieViewModel));
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("search movies", error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
//        moviesRecyclerView.setAdapter(new MovieListAdapter(movies, MovieListAdapter.ListType.RECOMMENDATIONS));
    }


}