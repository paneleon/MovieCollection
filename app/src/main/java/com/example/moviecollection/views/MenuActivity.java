package com.example.moviecollection.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.moviecollection.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String[] menuOptions = new String[]{
                "To-watch",
                "Recommendations",
                "Search",
                "Watched",
                "Favorites",
                "Craving Popcorn?"
        };

        ListView menu = findViewById(R.id.main_menu);
        menu.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuOptions));

        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent = null;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        intent = new Intent(MenuActivity.this, MovieListActivity.class);
                        break;
                    case 1:
                        intent = new Intent(MenuActivity.this, RecommendationsActivity.class);
                        break;
                    case 2:
                        intent = new Intent(MenuActivity.this, MovieSearchActivity.class);
                        break;
                    case 3:
                        intent = new Intent(MenuActivity.this, WatchedMoviesActivity.class);
                        break;
                    case 4:
                        intent = new Intent(MenuActivity.this, FavoriteMoviesActivity.class);
                        break;
                    case 5:
                        intent = new Intent(MenuActivity.this, SnacksActivity.class);
                        break;
                }

                startActivity(intent);
            }
        });
    }
}