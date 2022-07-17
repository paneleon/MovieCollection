package com.example.moviecollection.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviecollection.R;
import com.example.moviecollection.helpers.MovieListAdapter;
import com.example.moviecollection.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

public class MovieSearchActivity extends AppCompatActivity {

    String searchText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);



        ImageButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = ((EditText)findViewById(R.id.search_text)).getText().toString();
                searchMovie(searchText);
            }
        });


    }

    private void searchMovie(String title){

        System.out.println("title " + title);

        String endpoint = String.format("https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=%s", title);
        String endpoint2 = "https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher";

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
//                        movieList.add(new Movie(Integer.parseInt(jsonObject.getString("id")),
//                                        jsonObject.getString("title"),
//                                        jsonObject.getString("overview")
//                                )
//                        );
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
//                moviesRecyclerView.setAdapter(new MovieListAdapter(movieList));
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("my-api",error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


}