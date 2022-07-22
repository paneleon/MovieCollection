package com.example.moviecollection.repositories;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviecollection.adapters.MovieListAdapter;
import com.example.moviecollection.model.Movie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieRepository {

//    private static volatile MovieRepository INSTANCE;
    private static final String DB_NAME = "Movies";
    private static final String DB_URL = "https://moviecollection-d43bf-default-rtdb.firebaseio.com/";
    private static final String API_BASE_URL = "https://api.themoviedb.org/3";
    private static final String API_KEY = "";
    FirebaseDatabase database;
    public static DatabaseReference dbRef;
    private MutableLiveData<ArrayList<Movie>> movies = new MutableLiveData<ArrayList<Movie>>();
    private ArrayList<Movie> moviesArray = new ArrayList<Movie>();
    private Context context;
    RequestQueue requestQueue;
    private int totalPagesResponse = 1;

//    private ValueEventListener listener;

    public MovieRepository(Context context){
        database = FirebaseDatabase.getInstance(DB_URL);
        dbRef = database.getReference(DB_NAME);
        this.context = context;
    }

//    public void addListener(ValueEventListener listener) {
//        dbRef.addValueEventListener(listener);
//    }

//    public void removeListener() {
//        dbRef.removeEventListener(listener);
//    }

    public MutableLiveData<ArrayList<Movie>> getMoviesToWatch(){
        dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        Movie movie = child.getValue(Movie.class);
                        movie.setKey(child.getKey());
                        moviesArray.add(movie);
                    }
                    movies.setValue(moviesArray);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w("Movies To Watch", "Failed to read values.", error.toException());
                }
            });
        return movies;
    }

    public MutableLiveData<ArrayList<Movie>> getFavoriteMovies(){
        Query favoriteMovieQuery = dbRef.orderByChild("favorite").equalTo(true);
        favoriteMovieQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                moviesArray.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    System.out.println("-------" + snapshot.toString());
                    Movie movie = snapshot.getValue(Movie.class);
                    movie.setKey(snapshot.getKey());
                    moviesArray.add(movie);
                }
                movies.setValue(moviesArray);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Favorite Movies", "Failed to read values.", error.toException());
            }
        });
        return movies;
    }

    public MutableLiveData<ArrayList<Movie>> getWatchedMovies(){
        Query watchedMoviesQuery = dbRef.orderByChild("seen").equalTo(true);
        watchedMoviesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                moviesArray.clear();
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Movie movie = child.getValue(Movie.class);
                    movie.setKey(child.getKey());
                    moviesArray.add(movie);
                }
                movies.setValue(moviesArray);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Watched Movies", "Failed to read values.", error.toException());
            }
        });
        return movies;
    }


    public void addMovie(Movie movie){
        dbRef.push().setValue(movie);
    }

    public void removeMovie(String key){
        dbRef.child(key).removeValue();
    }

    public void setFavorite(String key, boolean state){
        dbRef.child(key).child("favorite").setValue(state);
    }

    public void setSeen(String key, boolean state){
        dbRef.child(key).child("seen").setValue(state);
    }

    // retrieving data from apis
    public MutableLiveData<ArrayList<Movie>> searchMovie(String title){
        String formattedTitle = title.trim().replace(' ', '+');
        String endpoint = String.format("%s/search/movie?api_key=%s&query=%s", API_BASE_URL, API_KEY, formattedTitle);

        requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, endpoint,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject jsonObject = results.getJSONObject(i);

                            Log.d("search movies", "total ==== " + response.getString("total_results"));
                            Log.d("search movies", "page ==== " + response.getString("page"));
                            Log.d("search movies", "total pages ==== " + response.getString("total_pages"));

                            Log.d("search movies", "==== " + jsonObject.getString("id"));
                            moviesArray.add(new Movie(Integer.parseInt(jsonObject.getString("id")),
                                            jsonObject.getString("title"),
                                            jsonObject.getString("overview"),
                                            (jsonObject.has("release_date") ? jsonObject.getString("release_date") : ""),
                                            Double.parseDouble(jsonObject.getString("vote_average"))
                                    )
                            );
                    }
                        movies.setValue(moviesArray);

                } catch (Exception e){
                    e.printStackTrace();
                    movies.setValue(null);
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Search Movies", error.getMessage());
                movies.setValue(null);
            }
        });
        requestQueue.add(jsonObjectRequest);
        return movies;
    }

    public Pair<MutableLiveData<ArrayList<Movie>>, Integer> getPopularMovies(int page) {
        String endpoint = String.format("%s/movie/popular?api_key=%s&language=en-US&page=%d", API_BASE_URL, API_KEY, page);
        requestQueue = Volley.newRequestQueue(context);

//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, endpoint,null, new Response.Listener<JSONArray>(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, endpoint,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    totalPagesResponse = Integer.parseInt(response.getString("total_pages")); // the max is 500 according to docs

                    JSONArray results = response.getJSONArray("results");
                    System.out.println("TOTAL POPULAR1 " + totalPagesResponse);
                    Log.d("search movies", "total pages ==== " + response.getString("total_pages"));
                    for (int i = 0; i < results.length(); i++) {
                            JSONObject jsonObject = results.getJSONObject(i);
                            Log.d("my-api", "==== " + jsonObject.getString("title"));
                            moviesArray.add(new Movie(Integer.parseInt(jsonObject.getString("id")),
                                            jsonObject.getString("title"),
                                            jsonObject.getString("overview"),
                                            (jsonObject.has("release_date") ? jsonObject.getString("release_date") : ""),
                                            Double.parseDouble(jsonObject.getString("vote_average"))
                                    )
                            );
                        }
                        movies.setValue(moviesArray);
                } catch (Exception e){
                    e.printStackTrace();
                    movies.setValue(null);
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Popular Movies", error.getMessage());
                movies.setValue(null);
            }
        });
        requestQueue.add(jsonObjectRequest);
        return new Pair<>(movies, totalPagesResponse);
    }


    public Pair<MutableLiveData<ArrayList<Movie>>, Integer> getTopMovies(int page){
        String endpoint = String.format("%s/movie/top_rated?api_key=%s&language=en-US&page=%d", API_BASE_URL, API_KEY, page);
        requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, endpoint,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    Log.d("search movies", "total ==== " + response.getString("total_results"));
                    totalPagesResponse = Integer.parseInt(response.getString("total_pages")); // the max is 500 according to docs
//                    currentPageTop = ThreadLocalRandom.current().nextInt(1, totalPagesTop + 1);

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject jsonObject = results.getJSONObject(i);
                            Log.d("my-api", "==== " + jsonObject.getString("title"));
                            moviesArray.add(new Movie(Integer.parseInt(jsonObject.getString("id")),
                                            jsonObject.getString("title"),
                                            jsonObject.getString("overview"),
                                            (jsonObject.has("release_date") ? jsonObject.getString("release_date") : ""),
                                            Double.parseDouble(jsonObject.getString("vote_average"))
                                    )
                            );
                        }
                    movies.setValue(moviesArray);
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
        return new Pair<>(movies, totalPagesResponse);
    }
}
