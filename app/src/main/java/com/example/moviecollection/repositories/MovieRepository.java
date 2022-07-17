package com.example.moviecollection.repositories;

import android.content.Context;

import com.example.moviecollection.model.Movie;
import com.example.moviecollection.model.MovieDao;

public class MovieRepository {

    private final MovieDao movieDao;

    public MovieRepository(Context context){
        this.movieDao = MovieDao.getInstance();
    }

    public void addMovie(Movie movie){
        movieDao.addMovieToDB(movie);
    }
}
