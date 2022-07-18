package com.example.moviecollection.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.moviecollection.model.Movie;
import com.example.moviecollection.model.MovieDao;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    private final MovieDao movieDao;

    public MovieRepository(Context context){
        this.movieDao = MovieDao.getInstance();
    }

    public void addMovie(Movie movie){
        movieDao.addMovieToDB(movie);
    }

    public ArrayList<Movie> getArrayOfMovies(DataSnapshot snapshot){
        return movieDao.getArrayOfMovies(snapshot);
    }

    public void removeMovie(String key){
        movieDao.removeMovieFromDB(key);
    }
}
