package com.example.moviecollection.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.moviecollection.R;
import com.example.moviecollection.helpers.MovieListAdapter;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        RecyclerView menuRecyclerView = findViewById(R.id.movie_list);

        menuRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String[] movieList = new String[]{"Movie 1", "Movie 2", "Movie 3"};

        MovieListAdapter adapter = new MovieListAdapter(movieList);

        adapter.setClickListener(new MovieListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println(movieList[position]);
            }
        });
        menuRecyclerView.setAdapter(adapter);
    }
}