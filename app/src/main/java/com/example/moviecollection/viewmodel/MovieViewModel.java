package com.example.moviecollection.viewmodel;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviecollection.model.Movie;
import com.example.moviecollection.repositories.MovieRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    public MovieRepository movieRepository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
    }

    public void addMovie(Movie movie){
        movieRepository.addMovie(movie);
    }

    public MutableLiveData<ArrayList<Movie>> getMoviesToWatch() {
        return movieRepository.getMoviesToWatch();
    }

    public MutableLiveData<ArrayList<Movie>> getFavoriteMovies(){
        return movieRepository.getFavoriteMovies();
    }

    public MutableLiveData<ArrayList<Movie>> getWatchedMovies(){
        return movieRepository.getWatchedMovies();
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

    public MutableLiveData<ArrayList<Movie>> searchMovie(String title){
        return movieRepository.searchMovie(title);
    }

    public Pair<MutableLiveData<ArrayList<Movie>>, Integer> getPopularMovies(int page) {
        return movieRepository.getPopularMovies(page);
    }

    public Pair<MutableLiveData<ArrayList<Movie>>, Integer> getTopMovies(int page) {
        return movieRepository.getTopMovies(page);
    }
}

