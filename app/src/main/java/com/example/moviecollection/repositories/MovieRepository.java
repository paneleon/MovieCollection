package com.example.moviecollection.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.moviecollection.model.Movie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class MovieRepository {

//    private static volatile MovieRepository INSTANCE;
    private static final String DB_NAME = "Movies";
    private static final String DB_URL = "https://moviecollection-d43bf-default-rtdb.firebaseio.com/";
    private static String TAG = "Movie";
    FirebaseDatabase database;
    public static DatabaseReference dbRef;
    private MutableLiveData<ArrayList<Movie>> movies = new MutableLiveData<ArrayList<Movie>>();
    private ArrayList<Movie> moviesArray = new ArrayList<Movie>();
//    private ValueEventListener listener;

    public MovieRepository(Context context){
        database = FirebaseDatabase.getInstance(DB_URL);
        dbRef = database.getReference(DB_NAME);
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

}
