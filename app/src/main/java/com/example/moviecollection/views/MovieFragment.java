package com.example.moviecollection.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviecollection.R;
import com.example.moviecollection.databinding.FragmentMovieBinding;
import com.example.moviecollection.model.Movie;

public class MovieFragment extends Fragment {

    Movie movieData;

    public MovieFragment() {
        // Required empty public constructor
    }

    public MovieFragment(Movie movie){
        this.movieData = movie;
    }


    public static MovieFragment newInstance(String param1, String param2) {
        MovieFragment fragment = new MovieFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        DataBinding
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        FragmentMovieBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_movie, container, false);

        View view = binding.getRoot();
        //here data must be an instance of the class MarsDataProvider
        binding.setMovie(movieData);
        return view;


        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_movie, container, false);
    }
}