package com.example.moviecollection.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviecollection.model.Movie;
import com.example.moviecollection.repositories.MovieRepository;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    public MovieRepository movieRepository;
    private static MutableLiveData<List<Movie>> movies;
    private static final String TAG = "MoviesUpdate";

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public void addMovie(Movie movie){
        movieRepository.addMovie(movie);
    }

//    public ArrayList<Movie> getMovies(DataSnapshot snapshot){
//        return movieRepository.getMovies();
//    }

    public ArrayList<Movie> getArrayOfMovies(DataSnapshot snapshot) {
        return movieRepository.getArrayOfMovies(snapshot);
    }

    public void removeMovie(String key){
        movieRepository.removeMovie(key);
    }

    public void setFavorite(String key, boolean state){
        movieRepository.setFavorite(key, state);
    }

    public void setSeen(String key, boolean state){
        movieRepository.setSeen(key, state);
    }
}

