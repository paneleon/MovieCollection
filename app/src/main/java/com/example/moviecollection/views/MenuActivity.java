package com.example.moviecollection.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
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
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}