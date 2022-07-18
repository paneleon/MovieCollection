package com.example.moviecollection.model;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MovieDao {
    private static volatile MovieDao INSTANCE;
    private static final String DB_NAME = "Movies";
    private static final String DB_URL = "https://moviecollection-d43bf-default-rtdb.firebaseio.com/";
    private static String TAG = "Movie";

    FirebaseDatabase database;
    public static DatabaseReference dbRef;
    private ArrayList<Movie> movies = new ArrayList<>();

    private MovieDao(){
        database = FirebaseDatabase.getInstance(DB_URL);
        dbRef = database.getReference(DB_NAME);


//        dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//            for (DataSnapshot child: dataSnapshot.getChildren()) {
//                Movie value = child.getValue(Movie.class);
//                movies.add(value);
//            }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
    }

    public static MovieDao getInstance(){
        if (INSTANCE == null) {
            //Create database object
            INSTANCE = new MovieDao();
        }
        return INSTANCE;
    }

    public void addMovieToDB(Movie movie){
        dbRef.push().setValue(movie);
    }


    public ArrayList<Movie> getArrayOfMovies(DataSnapshot snapshot) {
        for (DataSnapshot child: snapshot.getChildren()) {
            movies.add(child.getValue(Movie.class));
        }
        return movies;
    }
}
