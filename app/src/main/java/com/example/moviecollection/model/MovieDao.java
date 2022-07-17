package com.example.moviecollection.model;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class MovieDao {
    private static volatile MovieDao INSTANCE;
    private static final String DB_NAME = "Movies";
    private static final String DB_URL = "https://moviecollection-d43bf-default-rtdb.firebaseio.com/";

    FirebaseDatabase database;
    DatabaseReference dbRef;

    private MovieDao(){
        database = FirebaseDatabase.getInstance(DB_URL);
        dbRef = database.getReference(DB_NAME);
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


//    public List<Movie> getMoviesFromDB(Movie movie) {
//
//        dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                HashMap<String, Movie> movies = (HashMap<String, Movie>) dataSnapshot.getValue();
//                StringBuilder output = new StringBuilder();
//
//                if (names != null) {
//                    for (String person : names.keySet()) {
//                        Log.d(TAG, "Key is: " + person);
//                        Log.d(TAG, "Value is: " + names.get(person));
//                        output.append(names.get(person)).append("\n");
//                    }
//                }
//                tv.setText(output.toString());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//    }

}
