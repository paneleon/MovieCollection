package com.example.moviecollection.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviecollection.R;
import com.example.moviecollection.model.Movie;
import com.example.moviecollection.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    public enum ListType {
        SAVED,
        FAVORITES,
        WATCHED,
        RECOMMENDATIONS
    }

//    Map<Boolean, Integer> buttonColors  = new HashMap<Boolean, Integer>() {{
//        put(true, Color.BLUE);
//        put(false, Color.GREEN);
//    }};

    Map<Boolean, Integer> buttonColors  = new HashMap<Boolean, Integer>() {{
        put(true, R.color.light_gray);
        put(false, R.color.white);
    }};

    private ArrayList<Movie> movies;
    ListType listType = ListType.SAVED;

    private ItemClickListener clickListener;
    MovieViewModel movieViewModel;

    public MovieListAdapter(ArrayList<Movie> movies){
        this.movies = movies;
    }

    public MovieListAdapter(ArrayList<Movie> movies, ListType listType){
        this.movies = movies;
        this.listType = listType;
    }

    public MovieListAdapter(ArrayList<Movie> movies, ListType listType, MovieViewModel viewModel){
        this.movies = movies;
        this.listType = listType;
        this.movieViewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_movie_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie item = movies.get(position);

        if (item.getTitle() != null) {
            holder.titleTextView.setText(item.getTitle());
        }

        if (item.getOverview() != null) {
            holder.overviewTextView.setText(item.getOverview());
        }

        if (item.getReleaseDate() != null) {
            holder.yearTextView.setText(item.getReleaseDate());
        }

        if (item.getVoteAverage() != 0) {
            holder.ratingTextView.setText(String.valueOf(item.getVoteAverage()));
        }

        holder.counterTextView.setText(String.valueOf(position + 1));

        holder.addButton.setOnClickListener(v -> holder.addButtonClick(item));

        holder.seenButton.setOnClickListener(v -> holder.seenButtonClick(item));

        holder.favoritesButton.setOnClickListener(v -> holder.favoritesButtonClick(item));

        holder.deleteButton.setOnClickListener(v -> holder.deleteButtonClick(item.getKey()));

        holder.favoritesButton.setBackgroundResource(buttonColors.get(item.getFavorite()));
        holder.seenButton.setBackgroundResource(buttonColors.get(item.getSeen()));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


//     parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return String.valueOf(movies.get(id));
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView counterTextView;
        TextView titleTextView;
        TextView overviewTextView;
        TextView ratingTextView;
        TextView yearTextView;

        ImageButton addButton;
        ImageButton seenButton;
        ImageButton favoritesButton;
        ImageButton deleteButton;

        ViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.movie_title);
            overviewTextView = itemView.findViewById(R.id.movie_overview);
            ratingTextView = itemView.findViewById(R.id.movie_rating);
            yearTextView = itemView.findViewById(R.id.movie_year);
            counterTextView = itemView.findViewById(R.id.movie_item_counter);

            addButton = itemView.findViewById(R.id.movie_item_add_button);
            seenButton = itemView.findViewById(R.id.movie_item_seen_button);
            favoritesButton = itemView.findViewById(R.id.movie_item_favorite_button);
            deleteButton = itemView.findViewById(R.id.movie_item_remove_button);

            switch(listType){
                case SAVED:
                    addButton.setVisibility(View.INVISIBLE);
                    break;
                case RECOMMENDATIONS:
                    seenButton.setVisibility(View.INVISIBLE);
                    favoritesButton.setVisibility(View.INVISIBLE);
                    deleteButton.setVisibility(View.INVISIBLE);
                    break;
                case FAVORITES:
                    addButton.setVisibility(View.INVISIBLE);
                    seenButton.setVisibility(View.INVISIBLE);
                    favoritesButton.setVisibility(View.INVISIBLE);
                    break;
                case WATCHED:
                    addButton.setVisibility(View.INVISIBLE);
                    seenButton.setVisibility(View.INVISIBLE);
                    break;
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }

        public void addButtonClick(Movie movie){
            System.out.println("Add button was clicked");
            movieViewModel.addMovie(movie);
        }

        public void deleteButtonClick(String key){
            System.out.println("Delete button was clicked");

            if (listType == ListType.FAVORITES){
                movieViewModel.setFavorite(key, false);
            } else if (listType == ListType.WATCHED){
                movieViewModel.setSeen(key, false);
            } else {
                movieViewModel.removeMovie(key);
            }
        }

        public void favoritesButtonClick(Movie movie){
            System.out.println("Favorites button was clicked");
            movie.setFavorite(!movie.getFavorite());
            movieViewModel.setFavorite(movie.getKey(), movie.getFavorite());

            favoritesButton.setBackgroundResource(buttonColors.get(movie.getSeen()));
        }

        public void seenButtonClick(Movie movie){
            System.out.println("Seen button was clicked");
            movie.setSeen(!movie.getSeen());
            movieViewModel.setSeen(movie.getKey(), movie.getSeen());

            seenButton.setBackgroundResource(buttonColors.get(movie.getSeen()));
        }
    }
}
